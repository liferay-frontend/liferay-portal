/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
/**
 * Align utility. Computes region or best region to align an element with
 * another. Regions are relative to viewport, make sure to use element with
 * position fixed, or position absolute when the element first positioned
 * parent is the body element.
 */
/**
 * Constants that represent the supported positions for `Align`.
 */
declare const ALIGN_POSITIONS: {
    Bottom: number;
    BottomCenter: number;
    BottomLeft: number;
    BottomRight: number;
    Left: number;
    LeftCenter: number;
    Right: number;
    RightCenter: number;
    Top: number;
    TopCenter: number;
    TopLeft: number;
    TopRight: number;
};
export { ALIGN_POSITIONS };
/**
 * Aligns the element with the best region around alignElement. The best
 * region is defined by clockwise rotation starting from the specified
 * `position`. The element is always aligned in the middle of alignElement
 * axis.
 */
export declare function align(element: HTMLElement, alignElement: HTMLElement, position: number, autoBestAlign?: boolean): number;
/**
 * Returns the best region to align element with alignElement. This is similar
 * to `suggestAlignBestRegion`, but it only returns the region information,
 * while `suggestAlignBestRegion` also returns the chosen position.
 */
export declare function getAlignBestRegion(element: HTMLElement, alignElement: HTMLElement, position: number): {
    bottom: number;
    height: number;
    left: number;
    right: number;
    top: number;
    width: number;
};
/**
 * Returns the region to align element with alignElement. The element is
 * always aligned in the middle of alignElement axis.
 */
export declare function getAlignRegion(element: HTMLElement, alignElement: HTMLElement, position: number): {
    bottom: number;
    height: number;
    left: number;
    right: number;
    top: number;
    width: number;
};
/**
 * Looks for the best region for aligning the given element. The best
 * region is defined by clockwise rotation starting from the specified
 * `position`. The element is always aligned in the middle of alignElement
 * axis.
 */
export declare function suggestAlignBestRegion(element: HTMLElement, alignElement: HTMLElement, position: number): {
    position: number;
    region: {
        bottom: number;
        height: number;
        left: number;
        right: number;
        top: number;
        width: number;
    };
};
