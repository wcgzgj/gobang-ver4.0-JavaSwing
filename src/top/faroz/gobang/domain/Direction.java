package top.faroz.gobang.domain;

/**
 * 这个枚举类没什么用
 * 就是来提醒我自己  Lay,Stand,Main,Vice  四个方向的Pre在哪里
 */
public enum Direction {
    Lay,Stand,Main,Vice


    /**
     *Main Stand  Vice
     *   *  *  |
     *    * * |
     *     **|
     *   ****---   Lay
     *     *|\
     *    * | \
     *   *  |  \
     *
     *     * 号标识出的都是四个方向中的Pre方向
     *     | 号标识出的都是四个方向中的next方向
     */

}
