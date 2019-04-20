package com.nadisam.citybombing.levels.utils;

public class Defines
{
    /************* POINTS ACHIVEMENT *****/
    public static final int     MIN_POINTS_FOR_PLANE_2    = 10000;
    public static final int     MIN_POINTS_FOR_ENTERPRISE = 20000;

    /************* INTENTS BUNDLE ********/
    public static final String  NUM_LEVEL_EXTRA           = "num_level_extra";
    public static final String  PLANE_TYPE_EXTRA          = "plane_type_extra";

    /************* CONSTANTS *************/
    public static double        MM_PER_INCH               = 25.4;
    public static int           FRAME_RATE                = 50;

    /************* GAMES *****************/

    public static final int     NUM_LEVELS                = 200;
    public static final int     MIN_DIFFICULTY            = 120;
    public static final int     MAX_DIFFICULTY            = 600;

    /************* MUSIC *****************/
    public static final int     MUSIC                     = 3;

    /************* FLOOR *****************/

    public static final int     NO_FLOOR_IMPACT           = -2;
    public static final int     NO_FLOOR                  = -1;
    public static final int     FLOOR_ROOF_A              = 0;
    public static final int     FLOOR_ROOF_B              = 1;
    public static final int     FLOOR_ROOF_C              = 2;
    public static final int     FLOOR_ROOF_D              = 3;
    public static final int     FLOOR_ROOF_E              = 4;
    public static final int     FLOOR_ROOF_F              = 5;
    public static final int     FLOOR_ROOF_G              = 6;

    public static final int     NUM_ROOF_TYPES            = 7;

    public static final int     FLOOR_TYPE_A              = 7;
    public static final int     FLOOR_TYPE_B              = 8;
    public static final int     FLOOR_TYPE_C              = 9;

    public static final int     NUM_FLOOR_TYPES           = 3;

    public static final int     FLOOR_BAJO_TYPE_A         = 10;
    public static final int     FLOOR_BAJO_TYPE_B         = 11;
    public static final int     FLOOR_BAJO_TYPE_C         = 12;

    public static final int     NUM_BAJO_TYPES            = 3;

    public static final int     FLOOR_EXTRA_TYPE_A        = 13;
    public static final int     FLOOR_EXTRA_TYPE_B        = 14;
    public static final int     FLOOR_EXTRA_TYPE_C        = 15;

    public static final int     FLOOR_ROOF_ENEMY_TYPE_A   = 16;
    public static final int     FLOOR_ROOF_ENEMY_TYPE_B   = 17;

    public static final int     FLOOR_MIN_FLOORS          = 2;
    public static final int     FLOOR_MAX_FLOORS          = 4;

    public static final boolean FLOOR_IS_INTACT           = true;
    public static final boolean FLOOR_DESTROYED           = false;
    public static final int     DESTROYED_COUNTER         = 16;

    /************* GREEN BUILDINGS *******/
    public static final int     FLOOR_GREEN_CHURCH        = 18;
    public static final int     FLOOR_GREEN_SCHOOL        = 19;
    public static final int     FLOOR_GREEN_HOSPITAL      = 20;

    /************* PLANE *****************/

    public static final int     PLANE_TYPE_A              = 0;
    public static final int     PLANE_TYPE_B              = 1;
    public static final int     PLANE_TYPE_C              = 2;
    public static final int     PLANE_SPEED               = 35;                 // mm per second
    public static final int     PLANE_ALTITUDE_GAP        = 120;                // pixels per round

    /********** PLANE TYPE A **************/

    public static final int     PLANE_TYPE_A_SPEED        = 10;
    public static final int     PLANE_TYPE_A_MAX_SHOTS    = 10;
    public static final int     PLANE_TYPE_A_BOMP         = Defines.BOMB_TYPE_A;

    /********** PLANE TYPE B **************/

    public static final int     PLANE_TYPE_B_SPEED        = 10;
    public static final int     PLANE_TYPE_B_MAX_SHOTS    = 3;                 // 3
    public static final int     PLANE_TYPE_B_BOMP         = Defines.BOMB_TYPE_B;

    /********** PLANE TYPE C **************/

    public static final int     PLANE_TYPE_C_SPEED        = 10;
    public static final int     PLANE_TYPE_C_MAX_SHOTS    = 20;
    public static final int     PLANE_TYPE_C_BOMP         = Defines.BOMB_TYPE_C;

    /************* BOMB *****************/

    public static final int     BOMB_TYPE_A               = 0;
    public static final int     BOMB_TYPE_B               = 1;
    public static final int     BOMB_TYPE_C               = 2;
    public static final float   BOMB_TIME_GAP             = 0.04f;
    public static final int     BOMB_ACCELERATION         = 12;
    public static final int     BOMB_INITIAL_SPEED        = 40;
    public static final int     BOMB_TYPE_A_POWER         = 1;                  // 1
    public static final int     BOMB_TYPE_B_POWER         = 3;
    public static final int     BOMB_TYPE_C_POWER         = 2;

    /************* STRENGTH *****************/
    public static final int     MAX_STRENGTH_LEVEL        = 4;
    public static final int     MIN_STRENGTH_LEVEL        = 1;

    /************* ENEMIES *****************/
    public static final String  ENEMY_AIRCRAFT_BATTERY    = "aircraft_baattery";
    public static final String  ENEMY_BALOON_MINES        = "baloon_mines";
    public static final int     ENEMY_BULLET_SPEED        = 40;
    public static final int     ENEMY_BULLET_MIN_RELOAD   = 25;                 // In frames per second
    public static final int     ENEMY_BULLET_MAX_RELOAD   = 50;                 // In frames per second

    /************* EXTRAS *****************/
    public static final String  EXTRA_LOW_SPEED           = "low_speed";
    public static final String  EXTRA_MORE_SHOOTS         = "more_shoots";
    public static final String  EXTRA_POWER_BOMBS         = "power_bombs";

}
