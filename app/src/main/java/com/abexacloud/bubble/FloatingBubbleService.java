package com.abexacloud.bubble;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abexacloud.bubble.adapter.CustomAdapter;

public class FloatingBubbleService extends Service {
    protected static final String TAG = FloatingBubbleService.class.getSimpleName();

    // Constructor Variable
    protected FloatingBubbleLogger logger;

    // The Window Manager View
    protected WindowManager windowManager;

    // The layout inflater
    protected LayoutInflater inflater;

    // Window Dimensions
    protected Point windowSize = new Point();

    // The Views
    protected View bubbleView;
    protected View removeBubbleView;
    protected View expandableView;

    protected WindowManager.LayoutParams bubbleParams;
    protected WindowManager.LayoutParams removeBubbleParams;
    protected WindowManager.LayoutParams expandableParams;

    private FloatingBubbleConfig config;
    private FloatingBubblePhysics physics;
    private FloatingBubbleTouch touch;

    @Override
    public void onCreate() {
        super.onCreate();
        logger = new FloatingBubbleLogger().setDebugEnabled(true).setTag(TAG);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null || !onGetIntent(intent)) {
            return Service.START_NOT_STICKY;
        }

        Log.d(TAG, "Start with START_STICKY");

        // Remove existing views
        removeAllViews();

        // Load the Window Managers
        setupWindowManager();
        setupViews();
        setTouchListener();
        return super.onStartCommand(intent, flags, Service.START_STICKY);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.log("onDestroy");
        removeAllViews();
    }

    private void removeAllViews() {
        if (windowManager == null) {
            return;
        }

        if (bubbleView != null) {
            windowManager.removeView(bubbleView);
            bubbleView = null;
        }

        if (removeBubbleView != null) {
            windowManager.removeView(removeBubbleView);
            removeBubbleView = null;
        }

        if (expandableView != null) {
            windowManager.removeView(expandableView);
            expandableView = null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void setupWindowManager() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        setLayoutInflater();
        windowManager.getDefaultDisplay().getSize(windowSize);
    }

    protected LayoutInflater setLayoutInflater() {
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        return inflater;
    }

    /**
     * Creates the views
     */
    protected void setupViews() {
        config = getConfig();
        int padding = dpToPixels(config.getPaddingDp());
        int iconSize = dpToPixels(config.getBubbleIconDp());
        int bottomMargin = getExpandableViewBottomMargin();

        // Setting up view
        bubbleView = inflater.inflate(R.layout.floating_bubble_view, null);
        removeBubbleView = inflater.inflate(R.layout.floating_remove_bubble_view, null);
        expandableView = inflater.inflate(R.layout.floating_expandable_view, null);

        // Setting up the Remove Bubble View setup
        removeBubbleParams = getDefaultWindowParams();
        removeBubbleParams.gravity = Gravity.TOP | Gravity.START;
        removeBubbleParams.width = dpToPixels(config.getRemoveBubbleIconDp());
        removeBubbleParams.height = dpToPixels(config.getRemoveBubbleIconDp());
        removeBubbleParams.x = (windowSize.x - removeBubbleParams.width) / 2; // Permite centrar el icono en X
        removeBubbleParams.y = windowSize.y - removeBubbleParams.height - bottomMargin; // Permite ubicarlo en la parte inferior de la pantalla
        removeBubbleView.setVisibility(View.GONE);
        removeBubbleView.setAlpha(config.getRemoveBubbleAlpha()); // Permite cambiar el alpha (transparencia) del icono
        windowManager.addView(removeBubbleView, removeBubbleParams); //

        // Setting up the Expandable View setup
        expandableParams = getDefaultWindowParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        expandableParams.height = windowSize.y - iconSize - bottomMargin;
        expandableParams.gravity = Gravity.TOP | Gravity.START;
        expandableView.setVisibility(View.GONE);
        ((LinearLayout) expandableView).setGravity(config.getGravity());
        expandableView.setPadding(padding, padding, padding, padding);

        // build dinamic items
        RecyclerView rvOptions = expandableView.findViewById(R.id.rvOptions);   // binding
        rvOptions.setLayoutManager(new LinearLayoutManager(this));
        rvOptions.setAdapter(new CustomAdapter(new String[]{"Item1","Item2","Item3"}));

        windowManager.addView(expandableView, expandableParams);

        // Setting up the Floating Bubble View
        bubbleParams = getDefaultWindowParams();
        bubbleParams.gravity = Gravity.TOP | Gravity.START;
        bubbleParams.width = iconSize;
        bubbleParams.height = iconSize;
        windowManager.addView(bubbleView, bubbleParams);

        // Setting the configuration
        if (config.getRemoveBubbleIcon() != null) {
            ((ImageView) removeBubbleView).setImageDrawable(config.getRemoveBubbleIcon());
        }
        if (config.getBubbleIcon() != null) {
            ((ImageView) bubbleView).setImageDrawable(config.getBubbleIcon());
        }
        // Se inyectara el contenido en el expandableVie
        // CardView card = expandableView.findViewById(R.id.expandableViewCard);
        // card.setRadius(dpToPixels(config.getBorderRadiusDp())); // Permite cambiar el radio del borde del card
        // ImageView triangle = expandableView.findViewById(R.id.expandableViewTriangle);
        // LinearLayout container = expandableView.findViewById(R.id.expandableViewContainer);
//        if (config.getExpandableView() != null) {
//            triangle.setColorFilter(config.getTriangleColor());
//            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) triangle.getLayoutParams();
//            Toast.makeText(this, "X: " + triangle.getDrawable().getIntrinsicWidth(), Toast.LENGTH_SHORT).show();
//            params.leftMargin = dpToPixels((config.getBubbleIconDp() - 16*2) / 2);
//            params.rightMargin = dpToPixels((config.getBubbleIconDp() - 16*2) / 2);
//            triangle.setVisibility(View.VISIBLE);
//            container.setVisibility(View.VISIBLE);
//            card.setVisibility(View.VISIBLE);
//            container.setBackgroundColor(config.getExpandableColor());
//            container.removeAllViews();
//            container.addView(config.getExpandableView());
//        } else {
//            triangle.setVisibility(View.GONE);
//            container.setVisibility(View.GONE);
//            card.setVisibility(View.GONE);
//        }
    }

    /**
     * Get the Bubble config
     *
     * @return the config
     */
    protected FloatingBubbleConfig getConfig() {
        return FloatingBubbleConfig.getDefault(getContext());
    }

    /**
     * Sets the touch listener
     */
    protected void setTouchListener() {
        physics = new FloatingBubblePhysics.Builder()
                .sizeX(windowSize.x)
                .sizeY(windowSize.y)
                .bubbleView(bubbleView)
                .config(config)
                .windowManager(windowManager)
                .build();

        touch = new FloatingBubbleTouch.Builder()
                .sizeX(windowSize.x)
                .sizeY(windowSize.y)
                .listener(getTouchListener())
                .physics(physics)
                .bubbleView(bubbleView)
                .removeBubbleSize(dpToPixels(config.getRemoveBubbleIconDp()))
                .windowManager(windowManager)
                .expandableView(expandableView)
                .removeBubbleView(removeBubbleView)
                .config(config)
                .marginBottom(getExpandableViewBottomMargin())
                .padding(dpToPixels(config.getPaddingDp()))
                .build();

        bubbleView.setOnTouchListener(touch);
    }

    /**
     * Gets the touch listener for the bubble
     *
     * @return the touch listener
     */
    public FloatingBubbleTouchListener getTouchListener() {
        return new DefaultFloatingBubbleTouchListener() {
            @Override
            public void onRemove() {
                stopSelf();
            }
        };
    }

    /**
     * Get the default window layout params
     *
     * @return the layout param
     */
    protected WindowManager.LayoutParams getDefaultWindowParams() {
        return getDefaultWindowParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /**
     * Get the default window layout params
     *
     * @return the layout param
     */
    protected WindowManager.LayoutParams getDefaultWindowParams(int width, int height) {
        return new WindowManager.LayoutParams(
                width,
                height,
                Build.VERSION.SDK_INT >= 26 ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);
    }

    /**
     * Handles the intent for the service (only if it is not null)
     *
     * @param intent the intent
     */
    protected boolean onGetIntent(@NonNull Intent intent) {
        return true;
    }

    /**
     * Get the layout inflater for view inflation
     *
     * @return the layout inflater
     */
    protected LayoutInflater getInflater() {
        return inflater == null ? setLayoutInflater() : inflater;
    }

    /**
     * Get the context for the service
     *
     * @return the context
     */
    protected Context getContext() {
        return getApplicationContext();
    }

    /**
     * Sets the state of the expanded view
     *
     * @param expanded the expanded view state
     */
    protected void setState(boolean expanded) {
        touch.setState(expanded);
    }

    /**
     * Get the expandable view's bottom margin
     *
     * @return margin
     */
    private int getExpandableViewBottomMargin() {
        Resources resources = getContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int navBarHeight = 0;
        if (resourceId > 0) {
            navBarHeight = resources.getDimensionPixelSize(resourceId);
        }

        return navBarHeight;
    }

    /**
     * Converts DPs to Pixel values
     *
     * @return the pixel value
     */
    private int dpToPixels(int dpSize) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dpSize * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}