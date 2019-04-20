package com.nadisam.citybombing.levels.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.nadisam.citybombing.AppCore;
import com.nadisam.citybombing.log.Logger;
import com.nadisam.citybombing.pro.R;

public class Utils
{
    public static Bitmap mPlaneTypeA;
    public static Bitmap mPlaneTypeB;
    public static Bitmap mPlaneTypeC;

    /******************************************************************************************/

    public static Bitmap mExtraPowerBombs;
    public static Bitmap mExtraLowSpeed;
    public static Bitmap mPlaneModeShots;

    /******************************************************************************************/

    public static Bitmap mEnemyAircrafBattery;
    public static Bitmap mPlaneBalloom;

    /******************************************************************************************/

    public static Bitmap mFloorRoofA = loadFloor(Defines.FLOOR_ROOF_A);
    public static Bitmap mFloorRoofB = loadFloor(Defines.FLOOR_ROOF_B);
    public static Bitmap mFloorRoofC = loadFloor(Defines.FLOOR_ROOF_C);
    public static Bitmap mFloorRoofD = loadFloor(Defines.FLOOR_ROOF_D);
    public static Bitmap mFloorRoofE = loadFloor(Defines.FLOOR_ROOF_E);
    public static Bitmap mFloorRoofF = loadFloor(Defines.FLOOR_ROOF_F);
    public static Bitmap mFloorRoofG = loadFloor(Defines.FLOOR_ROOF_G);
    public static Bitmap mFloorTypeA = loadFloor(Defines.FLOOR_TYPE_A);
    public static Bitmap mFloorTypeB = loadFloor(Defines.FLOOR_TYPE_B);
    public static Bitmap mFloorTypeC = loadFloor(Defines.FLOOR_TYPE_C);
    public static Bitmap mFloorBajoA = loadFloor(Defines.FLOOR_BAJO_TYPE_A);
    public static Bitmap mFloorBajoB = loadFloor(Defines.FLOOR_BAJO_TYPE_B);
    public static Bitmap mFloorBajoC = loadFloor(Defines.FLOOR_BAJO_TYPE_C);
    
    /****/
    
    public static Bitmap mGreenBuildingChurch = loadGreenBuilding(Defines.FLOOR_GREEN_CHURCH);
    public static Bitmap mGreenBuildingSchool = loadGreenBuilding(Defines.FLOOR_GREEN_SCHOOL);
    public static Bitmap mGreenBuildingHospital = loadGreenBuilding(Defines.FLOOR_GREEN_HOSPITAL);

    /******************************************************************************************/

    public static Bitmap mExplosionA = loadExplosion(Defines.BOMB_TYPE_A);
    public static Bitmap mExplosionB = loadExplosion(Defines.BOMB_TYPE_B);
    public static Bitmap mExplosionC = loadExplosion(Defines.BOMB_TYPE_C);
    public static Bitmap mBoomTypeA  = loadBomb(Defines.BOMB_TYPE_A);
    public static Bitmap mBoomTypeB  = loadBomb(Defines.BOMB_TYPE_B);
    public static Bitmap mBoomTypeC  = loadBomb(Defines.BOMB_TYPE_C);

    /******************************************************************************************/

    public static int    mScreenWidth;
    public static int    mScreenHeight;
    public static float  mDpi;

    public static void calculateDisplayMetrics(Context context)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
        mDpi = metrics.xdpi;
    }

    public static float getDpi()
    {
        return mDpi;
    }

    /******************************************************************************************/

    public static int buildingWidth()
    {
        try
        {
            if (null != mFloorBajoA)
            {
                return mFloorBajoA.getWidth();
            }
            else
            {
                mFloorBajoA = loadFloor(Defines.FLOOR_BAJO_TYPE_A);
                return mFloorBajoA.getWidth();
            }
        }
        catch (Exception e)
        {
            return 72;
        }
    }

    public static Bitmap loadFloor(int floorType)
    {
        try
        {
            switch (floorType)
            {

            case Defines.FLOOR_ROOF_A:
                if (null == mFloorRoofA)
                {
                    mFloorRoofA = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_roof_type_a);
                }
                return mFloorRoofA;

            case Defines.FLOOR_ROOF_B:
                if (null == mFloorRoofB)
                {
                    mFloorRoofB = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_roof_type_b);
                }
                return mFloorRoofB;

            case Defines.FLOOR_ROOF_C:
                if (null == mFloorRoofC)
                {
                    mFloorRoofC = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_roof_type_c);
                }
                return mFloorRoofC;

            case Defines.FLOOR_ROOF_D:
                if (null == mFloorRoofD)
                {
                    mFloorRoofD = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_roof_type_d);
                }
                return mFloorRoofD;

            case Defines.FLOOR_ROOF_E:
                if (null == mFloorRoofE)
                {
                    mFloorRoofE = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_roof_type_e);
                }
                return mFloorRoofE;

            case Defines.FLOOR_ROOF_F:
                if (null == mFloorRoofF)
                {
                    mFloorRoofF = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_roof_type_f);
                }
                return mFloorRoofF;

            case Defines.FLOOR_ROOF_G:
                if (null == mFloorRoofG)
                {
                    mFloorRoofG = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_type_c);
                }
                return mFloorRoofG;

            case Defines.FLOOR_TYPE_A:
                if (null == mFloorTypeA)
                {
                    mFloorTypeA = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_type_a);
                }
                return mFloorTypeA;

            case Defines.FLOOR_TYPE_B:
                if (null == mFloorTypeB)
                {
                    mFloorTypeB = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_type_b);
                }
                return mFloorTypeB;

            case Defines.FLOOR_TYPE_C:
                if (null == mFloorTypeC)
                {
                    mFloorTypeC = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_type_c);
                }
                return mFloorTypeC;

            case Defines.FLOOR_BAJO_TYPE_A:
                if (null == mFloorBajoA)
                {
                    mFloorBajoA = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_bajo_type_a);
                }
                return mFloorBajoA;

            case Defines.FLOOR_BAJO_TYPE_B:
                if (null == mFloorBajoB)
                {
                    mFloorBajoB = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_bajo_type_b);
                }
                return mFloorBajoB;

            case Defines.FLOOR_BAJO_TYPE_C:
                if (null == mFloorBajoC)
                {
                    mFloorBajoC = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_floor_bajo_type_c);
                }
                return mFloorBajoC;

            default:
                break;
            }
        }
        catch (Exception e)
        {
            Logger.error("ERROR: Loading floor bitmap", e);
        }
        return null;
    }
    
    public static Bitmap loadGreenBuilding(int greenType)
    {
        try
        {
            switch (greenType)
            {

            case Defines.FLOOR_GREEN_CHURCH:
                if (null == mGreenBuildingChurch)
                {
                    mGreenBuildingChurch = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_green_church);
                }
                return mGreenBuildingChurch;

            case Defines.FLOOR_GREEN_HOSPITAL:
                if (null == mGreenBuildingHospital)
                {
                    mGreenBuildingHospital = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_green_hospital);
                }
                return mGreenBuildingHospital;

            case Defines.FLOOR_GREEN_SCHOOL:
                if (null == mGreenBuildingSchool)
                {
                    mGreenBuildingSchool = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_green_school);
                }
                return mGreenBuildingSchool;
            default:
                break;
            }
        }
        catch (Exception e)
        {
            Logger.error("ERROR: Loading floor bitmap", e);
        }
        return null;
    }

    public static Bitmap loadPlane(int planeType)
    {
        try
        {
            switch (planeType)
            {
            case Defines.PLANE_TYPE_A:
                if (null == mPlaneTypeA)
                {
                    mPlaneTypeA = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_plane_type_a);
                }
                return mPlaneTypeA;

            case Defines.PLANE_TYPE_B:
                if (null == mPlaneTypeB)
                {
                    mPlaneTypeB = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_plane_type_b);
                }
                return mPlaneTypeB;

            case Defines.PLANE_TYPE_C:
                if (null == mPlaneTypeC)
                {
                    mPlaneTypeC = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_plane_type_e);
                }
                return mPlaneTypeC;

            default:
                break;
            }
        }
        catch (Exception e)
        {
            Logger.error("ERROR: Loading plane bitmap", e);
        }
        return null;
    }

    public static Bitmap loadBomb(int bombType)
    {
        try
        {
            switch (bombType)
            {
            case Defines.BOMB_TYPE_A:
                return BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_bomb_1);
            case Defines.BOMB_TYPE_B:
                return BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_bomb_2);
            case Defines.BOMB_TYPE_C:
                return BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_bomb_3);
            }
        }
        catch (Exception e)
        {
            Logger.error("ERROR: Loading bomb bitmap", e);
        }
        return null;
    }

    public static Bitmap loadExplosion(int explosionType)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        try
        {
            switch (explosionType)
            {
            case Defines.BOMB_TYPE_A:
                return BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_explosion_1, options);
            case Defines.BOMB_TYPE_B:
                return BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_explosion_2, options);
            case Defines.BOMB_TYPE_C:
                return BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_explosion_5, options);
            }
        }
        catch (Exception e)
        {
            Logger.error("ERROR: Loading explosion bitmap", e);
        }
        return null;
    }

    public static Bitmap loadExtra(String type)
    {
        try
        {
            if (0 == type.compareTo(Defines.EXTRA_POWER_BOMBS))
            {
                if (null == mExtraPowerBombs)
                {
                    mExtraPowerBombs = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_extra_boomb);
                }
                return mExtraPowerBombs;
            }
            else if (0 == type.compareTo(Defines.EXTRA_LOW_SPEED))
            {
                if (null == mExtraLowSpeed)
                {
                    mExtraLowSpeed = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_extra_more_slow);
                }
                return mExtraLowSpeed;
            }
            else if (0 == type.compareTo(Defines.EXTRA_MORE_SHOOTS))
            {
                if (null == mPlaneModeShots)
                {
                    mPlaneModeShots = BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_extra_more_shots);
                }
                return mPlaneModeShots;
            }
        }
        catch (Exception e)
        {
            Logger.error("ERROR: Loading extra bitmap", e);
        }
        return null;
    }

    public static Bitmap loadEnemy(String type, boolean left)
    {
        try
        {
            if (0 == type.compareTo(Defines.ENEMY_AIRCRAFT_BATTERY))
            {
                if (true == left)
                {
                    return BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_enemy_antycraft_battery_left);
                }
                else
                {
                    return BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_enemy_antycraft_battery_right);
                }
            }
            else if (0 == type.compareTo(Defines.ENEMY_BALOON_MINES))
            {
                return BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_enemy_antycraft_battery_left);
            }
        }
        catch (Exception e)
        {
            Logger.error("ERROR: Loading enemy bitmap", e);
        }
        return null;
    }

    public static Bitmap loadBullet(boolean left)
    {
        try
        {
            if (true == left)
            {
                return BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_bullet_to_left);
            }
            else
            {
                return BitmapFactory.decodeResource(AppCore.getContext().getResources(), R.drawable.ic_bullet_to_right);
            }
        }
        catch (Exception e)
        {
            Logger.error("ERROR: Loading bullet bitmap", e);
        }
        return null;
    }

    /****************************************** ADMOB ************************************************/

    public static void initAdMob2(FragmentActivity activity)
    {
        int mReleaseMode = activity.getResources().getInteger(R.integer.release_mode);
        int typeOfApplication = activity.getResources().getInteger(R.integer.application_vestion_free_or_pro);

        if (0 == typeOfApplication)
        {
            try
            {
                AdView adView = (AdView) activity.findViewById(R.id.postCardAdView);
                AdRequest adRequest = null;
                if (mReleaseMode == 0)
                {
                    adRequest = new AdRequest.Builder().addTestDevice("3ADF420210D330BBA6D461434A52BAC9").build();
                }
                else
                {
                    adRequest = new AdRequest.Builder().build();
                }
                adView.loadAd(adRequest);
            }
            catch (Exception e)
            {
                Logger.error("AdMob ERROR", e);
            }
        }
        else
        {
            try
            {
                AdView adView = (AdView) activity.findViewById(R.id.postCardAdView);
                adView.setVisibility(View.GONE);
            }
            catch (Exception e)
            {
                Logger.error("AdMob ERROR", e);
            }
        }
    }

    private static AdView adview;
    
    public static void destroyAdView(FragmentActivity activity)
    {
        if (adview != null)
        {
            adview.destroy();
        }
    }

    public static void initAdMob(FragmentActivity activity)
    {
        int mReleaseMode = activity.getResources().getInteger(R.integer.release_mode);
        int typeOfApplication = activity.getResources().getInteger(R.integer.application_vestion_free_or_pro);

        if (0 == typeOfApplication)
        {
            RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.postCardAdView);
            adview = new AdView(activity);
            adview.setAdSize(AdSize.SMART_BANNER);
            adview.setAdUnitId("ca-app-pub-4925617863683008/1993876376");
            final AdListener listener = new AdListener() {
                @Override
                public void onAdLoaded()
                {
                    // Here we will display the adview if there is ad
                    adview.setVisibility(View.VISIBLE);
                    super.onAdLoaded();
                }
            };

            adview.setAdListener(listener);

            // This needs to remove empty space when ad is not loaded, for example no internet conn.
            adview.setVisibility(View.GONE);

            layout.addView(adview);

            AdRequest adRequest = null;
            if (mReleaseMode == 0)
            {
                adRequest = new AdRequest.Builder().addTestDevice("3ADF420210D330BBA6D461434A52BAC9").build();
            }
            else
            {
                adRequest = new AdRequest.Builder().build();
            }
            adview.loadAd(adRequest);
        }
    }

    /********************* DYNAMICS ******************************/

    /**
     * Return normalised speed to make it independent from the device screen.
     * 
     * @param dpi Of the screen
     * @param speed In mm per second
     * @return speed In pixels per frame rate
     */
    public static double getNormSpeed(float dpi, int speed)
    {
        double normSpeed;
        normSpeed = (((float) speed * dpi) / (Defines.MM_PER_INCH * Defines.FRAME_RATE));
        return normSpeed;
    }
    
    /**
     * Returns the distance in pixels of a distance in mm
     * @param distance in mm
     * @return
     */
    public static double getNormDistance (float dpi, float distance)
    {
        double normDistance;
        normDistance = (distance*dpi)/Defines.MM_PER_INCH;
        return normDistance;
    }

    /**
     * Returns a list of integers randomly distributed between numElemenentsWhereToPlace
     * 
     * @param numElementsToPlace
     * @param numElementsWhereToPlace
     * @return
     */
    private static Random mRandom = new Random();

    public static List<Integer> getRandomItemsList(int numElementsToPlace, int numElementsWhereToPlace, List<Integer> skipItems)
    {
        List<Integer> itemList = new ArrayList<Integer>();

        // Generate random position for all extras
        if (0 < numElementsToPlace)
        {
            int numBuildingsPerExtra = Math.round(numElementsWhereToPlace / numElementsToPlace); // Get buildings in groups to avoid set extra in the same place

            for (int ex = 0; ex < numElementsToPlace; ex++)
            {
                int position = getRantomInt(numBuildingsPerExtra, skipItems, ex * numBuildingsPerExtra) + ex * numBuildingsPerExtra;
                itemList.add(position);
            }
        }

        // Barajar los resultados
        int barajado = mRandom.nextInt(numElementsToPlace);

        for (int b = 0; b < barajado; b++)
        {
            Integer a = itemList.remove(0);
            itemList.add(a);
        }

        return itemList;
    }
    
    // Returns a random number that it's not in the list
    private static int getRantomInt(int num, List<Integer> skip, int acc)
    {
        boolean ok = false;
        int next = 0;
        if (null == skip)
        {
            return mRandom.nextInt(num);
        }
        else
        {
            while (false == ok)
            {
                next = mRandom.nextInt(num);
                if ((false == skip.contains(next+acc))&&(false == skip.contains(next-1+acc))&&(false == skip.contains(next-2+acc)))
                {
                    ok = true;
                }
            }
        }
        return next;
    }
}
