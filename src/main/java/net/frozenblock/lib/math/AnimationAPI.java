/*
 * Copyright 2022 FrozenBlock
 * This file is part of FrozenLib.
 *
 * FrozenLib is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * FrozenLib is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with FrozenLib. If not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.lib.math;

import java.awt.geom.Point2D;

/**
 * This class is used to make animations with easings.
 * <p>
 * Defining a point A(x,y) and B(x,y) you can create an animation between those two points ( A.getY() won't affect the animation).
 * <p>
 * Learn more at <a href="https://github.com/LIUKRAST/AnimationAPI/blob/main/README.md">the README</a>
 *
 * @author LiukRast (2021-2022)
 * @since 4.0
 */
public final class AnimationAPI {
	private AnimationAPI() {
		throw new UnsupportedOperationException("AnimationAPI contains only static declarations.");
	}

    public static float relativeX(Point2D a, Point2D b, float x) {
        return (float) ((x - a.getX()) / (b.getX() - a.getX()));
    }

    /**
     * Generates a "random" number depending on another number.
     *
     * @deprecated Use seed() instead of this!
     **/
    @Deprecated
    public static float rawSeed(float seed) {
        float f = (float) Math.pow(Math.PI, 3);
        float linear = (seed + f) * f;
        float flat = (float) Math.floor(linear);
        return linear - flat;
    }

    /**
     * Executes {@link #rawSeed(float)} multiple times to make the number look more "random"
     **/
    public static float seed(float seed) {
        return rawSeed(rawSeed(rawSeed(seed)));
    }

    /**
     * Convert a 2D position with a seed in a resulting seed
     **/
    public static float seed2D(Point2D seed2d, float seed) {
        return rawSeed((float) seed2d.getX()) * rawSeed((float) seed2d.getX()) * rawSeed(seed);
    }

    public static float legAnimation(float base, float range, float frequency, float limbAngle, float limbDistance, boolean inverted) {
        float baseRange = 1.4F;
        float baseFrequency = 0.6662F;
        float wave = (float) Math.sin(limbAngle * (baseFrequency * frequency)) * (baseRange * range) * limbDistance;
        if (inverted) {
            return base + wave;
        } else {
            return base - wave;
        }
    }

    public static float legAnimation(float base, float range, float frequency, float limbAngle, float limbDistance) {
        return legAnimation(base, range, frequency, limbAngle, limbDistance, false);
    }

    public static float legAnimation(float base, float limbAngle, float limbDistance, boolean inverted) {
        return legAnimation(base, 1, 1, limbAngle, limbDistance, inverted);
    }

    public static float legAnimation(float base, float limbAngle, float limbDistance) {
        return legAnimation(base, limbAngle, limbDistance, false);
    }


    /**
     * SINE EASING - Generated using Math.sin()
     */
    public static float sineEaseIn(Point2D a, Point2D b, float x) {
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (1 - (float) Math.cos(Math.PI * (relativeX(a, b, x) / 2)));
        }
    }

    public static float sineEaseOut(Point2D a, Point2D b, float x) {
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * ((float) Math.sin(Math.PI * (relativeX(a, b, x) / 2)));
        }
    }

    public static float sineEaseInOut(Point2D a, Point2D b, float x) {
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (0.5F - ((float) Math.cos(Math.PI * relativeX(a, b, x)) / 2));
        }
    }
    // -------------------------------------------------------

    /**
     * POLYNOMIAL EASING - Generated by elevating x at a "c" value
     */
    public static float polyEaseIn(Point2D a, Point2D b, float x, float c) {
        if (c < 0) {
            System.out.println("Animation API error - c must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * ((float) Math.pow(relativeX(a, b, x), c));
        }
    }

    public static float polyEaseOut(Point2D a, Point2D b, float x, float c) {
        if (c < 0) {
            System.out.println("Animation API error - c must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (1 - (float) Math.pow(-(relativeX(a, b, x) - 1), c));
        }
    }

    public static float polyEaseInOut(Point2D a, Point2D b, float x, float c) {
        if (c < 0) {
            System.out.println("Animation API error - c must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            if (x < (b.getX() - a.getX()) / 2) {
                return (float) b.getY() * (float) (Math.pow(2, c - 1) * Math.pow(relativeX(a, b, x), c));
            } else {
                return (float) b.getY() * (float) (1 - Math.pow(2 - 2 * relativeX(a, b, x), c) / 2);
            }
        }
    }
    // -------------------------------------------------------

    /**
     * QUADRATIC EASING - Generated using Poly and assuming c = 2
     */
    public static float quadraticEaseIn(Point2D a, Point2D b, float x) {
        return polyEaseIn(a, b, x, 2);
    }

    public static float quadraticEaseOut(Point2D a, Point2D b, float x) {
        return polyEaseOut(a, b, x, 2);
    }

    public static float quadraticEaseInOut(Point2D a, Point2D b, float x) {
        return polyEaseInOut(a, b, x, 2);
    }
    // -------------------------------------------------------

    /**
     * CUBIC EASING - Generated using Poly and assuming c = 3
     */
    public static float cubicEaseIn(Point2D a, Point2D b, float x) {
        return polyEaseIn(a, b, x, 3);
    }

    public static float cubicEaseOut(Point2D a, Point2D b, float x) {
        return polyEaseOut(a, b, x, 3);
    }

    public static float cubicEaseInOut(Point2D a, Point2D b, float x) {
        return polyEaseInOut(a, b, x, 3);
    }
    // -------------------------------------------------------

    /**
     * QUARTIC EASING - Generated using Poly and assuming c = 4
     */
    public static float quarticEaseIn(Point2D a, Point2D b, float x) {
        return polyEaseIn(a, b, x, 4);
    }

    public static float quarticEaseOut(Point2D a, Point2D b, float x) {
        return polyEaseOut(a, b, x, 4);
    }

    public static float quarticEaseInOut(Point2D a, Point2D b, float x) {
        return polyEaseInOut(a, b, x, 4);
    }
    // -------------------------------------------------------

    /**
     * QUINTIC EASING - Generated using Poly and assuming c = 5
     */
    public static float quinticEaseIn(Point2D a, Point2D b, float x) {
        return polyEaseIn(a, b, x, 5);
    }

    public static float quinticEaseOut(Point2D a, Point2D b, float x) {
        return polyEaseOut(a, b, x, 5);
    }

    public static float quinticEaseInOut(Point2D a, Point2D b, float x) {
        return polyEaseInOut(a, b, x, 5);
    }
    // -------------------------------------------------------

    /**
     * EXPONENTIAL EASING - Generated by 2^x
     */
    public static float expoEaseIn(Point2D a, Point2D b, float x) {
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (float) Math.pow(2, (10 * relativeX(a, b, x)) - 10);
        }
    }

    public static float expoEaseOut(Point2D a, Point2D b, float x) {
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (1 - (float) Math.pow(2, -10 * relativeX(a, b, x)));
        }
    }

    public static float expoEaseInOut(Point2D a, Point2D b, float x) {
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            if (x < (b.getX() - a.getX()) / 2) {
                return (float) b.getY() * (float) Math.pow(2, (20 * relativeX(a, b, x)) - 10) / 2;
            } else {
                return (float) b.getY() * (float) (2 - Math.pow(2, 10 - (20 * relativeX(a, b, x)))) / 2;
            }
        }
    }
    // -------------------------------------------------------

    /**
     * CICRULAR EASING - Uses Roots and Powers to make curves
     */
    public static float circEaseIn(Point2D a, Point2D b, float x, int roundness) {
        if (roundness < 0) {
            System.out.println("Animation API error - roundness must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (float) (1 - Math.pow(1 - Math.pow(relativeX(a, b, x), roundness), 1 / (float) roundness));
        }
    }

    public static float circEaseOut(Point2D a, Point2D b, float x, int roundness) {
        if (roundness < 0) {
            System.out.println("Animation API error - roundness must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (float) Math.pow(1 - Math.pow(relativeX(a, b, x) - 1, roundness), 1 / (float) roundness);
        }
    }

    public static float circEaseInOut(Point2D a, Point2D b, float x, int roundness) {
        if (roundness < 0) {
            System.out.println("Animation API error - roundness must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            if (x < (b.getX() - a.getX()) / 2) {
                return (float) b.getY() * (float) (1 - Math.pow(1 - Math.pow(2 * relativeX(a, b, x), roundness), 1 / (float) roundness)) / 2;
            } else {
                return (float) b.getY() * (float) (Math.pow(1 - Math.pow(-2 * relativeX(a, b, x) + 2, roundness), 1 / (float) roundness) + 1) / 2;
            }
        }
    }

    public static float circEaseIn(Point2D a, Point2D b, float x) {
        return circEaseIn(a, b, x, 2);
    }

    public static float circEaseOut(Point2D a, Point2D b, float x) {
        return circEaseOut(a, b, x, 2);
    }

    public static float circEaseInOut(Point2D a, Point2D b, float x) {
        return circEaseInOut(a, b, x, 2);
    }
    // -------------------------------------------------------

    /**
     * ELASTIC EASING - Generated by Cosine and a variable "c" of the curves intensity
     */
    public static float elasticEaseIn(Point2D a, Point2D b, float x, int c) {
        if (c < 0) {
            System.out.println("Animation API error - c must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (float) (Math.cos(2 * Math.PI * c * relativeX(a, b, x)) * relativeX(a, b, x));
        }
    }

    public static float elasticEaseOut(Point2D a, Point2D b, float x, int c) {
        if (c < 0) {
            System.out.println("Animation API error - c must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (float) (1 - (Math.cos(2 * Math.PI * c * relativeX(a, b, x)) * (1 - relativeX(a, b, x))));
        }
    }

    public static float elasticEaseInOut(Point2D a, Point2D b, float x, int c) {
        if (c < 0) {
            System.out.println("Animation API error - c must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (float) (relativeX(a, b, x) + (Math.sin(2 * Math.PI * c * relativeX(a, b, x)) * Math.sin(Math.PI * relativeX(a, b, x))));
        }
    }

    // Same Equations but automaticly defines
    public static float elasticEaseIn(Point2D a, Point2D b, float x) {
        int c = (int) (b.getX() - a.getX());
        return elasticEaseIn(a, b, x, c);
    }

    public static float elasticEaseOut(Point2D a, Point2D b, float x) {
        int c = (int) (b.getX() - a.getX());
        return elasticEaseOut(a, b, x, c);
    }

    public static float elasticEaseInOut(Point2D a, Point2D b, float x) {
        int c = (int) (b.getX() - a.getX());
        return elasticEaseInOut(a, b, x, c);
    }
    // -------------------------------------------------------

    /**
     * BOUNCE EASING - Generated by an elastic absoluted
     */
    public static float bounceEaseIn(Point2D a, Point2D b, float x, int c) {
        if (c < 0) {
            System.out.println("Animation API error - c must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (float) Math.abs(Math.cos(2 * Math.PI * c * relativeX(a, b, x)) * relativeX(a, b, x));
        }
    }

    public static float bounceEaseOut(Point2D a, Point2D b, float x, int c) {
        if (c < 0) {
            System.out.println("Animation API error - c must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (float) (1 - Math.abs(Math.cos(2 * Math.PI * c * relativeX(a, b, x)) * (1 - relativeX(a, b, x))));
        }
    }

    public static float bounceEaseInOut(Point2D a, Point2D b, float x, int c) {
        if (c < 0) {
            System.out.println("Animation API error - c must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (float) (relativeX(a, b, x) + Math.abs(Math.sin(2 * Math.PI * c * relativeX(a, b, x)) * Math.sin(Math.PI * relativeX(a, b, x))));
        }
    }

    // Same Equations but automatically defines c
    public static float bounceEaseIn(Point2D a, Point2D b, float x) {
        int c = (int) (b.getX() - a.getX());
        return elasticEaseIn(a, b, x, c);
    }

    public static float bounceEaseOut(Point2D a, Point2D b, float x) {
        int c = (int) (b.getX() - a.getX());
        return elasticEaseOut(a, b, x, c);
    }

    public static float bounceEaseInOut(Point2D a, Point2D b, float x) {
        int c = (int) (b.getX() - a.getX());
        return elasticEaseInOut(a, b, x, c);
    }
    // -------------------------------------------------------

    /**
     * BACK EASING - Generates a curve that comes back a little at the end (defined by an amount a >= 0)
     */
    public static float backEaseIn(Point2D a, Point2D b, float x, float c1) {
        float c2 = c1 + 1;
        if (c1 < 0) {
            System.out.println("Animation API error - c must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (float) (c2 * Math.pow(relativeX(a, b, x), 3) - c1 * Math.pow(relativeX(a, b, x) - 1, 2));
        }
    }

    public static float backEaseOut(Point2D a, Point2D b, float x, float c1) {
        float c2 = c1 + 1;
        if (c1 < 0) {
            System.out.println("Animation API error - c must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            return (float) b.getY() * (float) (1 + c2 * Math.pow(relativeX(a, b, x) - 1, 3) + c1 * Math.pow(relativeX(a, b, x) - 1, 2));
        }
    }

    public static float backEaseInOut(Point2D a, Point2D b, float x, float c1) {
        float c2 = c1 + 1;
        float c3 = c1 * 1.525F;
        if (c1 < 0) {
            System.out.println("Animation API error - c must be >= 0");
            return (float) Math.random();
        }
        if (x < a.getX()) {
            return 0; // before animation defining the eq as 0
        } else if (x > b.getX()) {
            return (float) b.getY(); // after animation defining the eq as b's Y
        } else {
            if (x < (b.getX() - a.getX()) / 2) {
                return (float) b.getY() * (float) (Math.pow(2 * relativeX(a, b, x), 2) * ((c3 + 1) * 2 * relativeX(a, b, x) - c3)) / 2;
            } else {
                return (float) b.getY() * (float) (Math.pow(2 * relativeX(a, b, x) - 2, 2) * ((c3 + 1) * (2 * relativeX(a, b, x) - 2) + c3) + 2) / 2;
            }
        }
    }

    // Same method but automatically defines c1
    public static float backEaseIn(Point2D a, Point2D b, float x) {
        return backEaseIn(a, b, x, 1.70158F);
    }

    public static float backEaseOut(Point2D a, Point2D b, float x) {
        return backEaseOut(a, b, x, 1.70158F);
    }

    public static float backEaseInOut(Point2D a, Point2D b, float x) {
        return backEaseInOut(a, b, x, 1.70158F);
    }
    // -------------------------------------------------------

    /**
     * LOOP SYSTEM
     * Loop: defines A and B and always repeat between these two values
     * Boomerang: creates a loop but instead of repeating it from start, it comes back and THEN loop
     */
    public static float line(Point2D a, Point2D b, float x) {
        return (float) (relativeX(a, b, x) * (b.getY() - a.getY()) + a.getY());
    }

    public static float flat(Point2D a, Point2D b, float x) {
        return (float) (Math.floor(relativeX(a, b, x)) * (b.getY() - a.getY()) + a.getY());
    }

    public static float flat2(Point2D a, Point2D b, float x) {
        return (float) (2 * Math.floor(relativeX(a, b, x) / 2) * (b.getY() - a.getY()) + a.getY());
    }

    public static float inverse(Point2D a, Point2D b, float x) {
        return (float) (flat(a, b, x) + b.getY() - line(a, b, x));
    }

    // BOOMERANG
    public static float boomerang(Point2D a, Point2D b, float x) {
        return line(a, b, x) - flat2(a, b, x) + a.getY() < b.getY() ? (float) (line(a, b, x) - flat2(a, b, x) + a.getY()) : inverse(a, b, x);
    }

    // LOOP
    public static float loop(Point2D a, Point2D b, float x) {
        return (float) (line(a, b, x) - flat(a, b, x) + a.getY());
    }
    // -------------------------------------------------------

    /*
     * Animation API - LiukRast, ALL RIGHTS RESERVED (2021-2022)
     */
}
