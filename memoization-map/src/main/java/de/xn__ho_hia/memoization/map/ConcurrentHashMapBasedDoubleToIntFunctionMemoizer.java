/*
 * This file is part of memoization.java. It is subject to the license terms in the LICENSE file found in the top-level
 * directory of this distribution and at http://creativecommons.org/publicdomain/zero/1.0/. No part of memoization.java,
 * including this file, may be copied, modified, propagated, or distributed except according to the terms contained
 * in the LICENSE file.
 */
package de.xn__ho_hia.memoization.map;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.function.DoubleToIntFunction;

import de.xn__ho_hia.quality.suppression.CompilerWarnings;

final class ConcurrentHashMapBasedDoubleToIntFunctionMemoizer
        extends ConcurrentHashMapBasedMemoizer<Double, Integer>
        implements DoubleToIntFunction {

    private final DoubleToIntFunction function;

    @SuppressWarnings(CompilerWarnings.NLS)
    ConcurrentHashMapBasedDoubleToIntFunctionMemoizer(
            final Map<Double, Integer> preComputedValues,
            final DoubleToIntFunction function) {
        super(preComputedValues);
        this.function = requireNonNull(function,
                "Cannot memoize a NULL DoubleToIntFunction - provide an actual DoubleToIntFunction to fix this.");
    }

    @Override
    public int applyAsInt(final double value) {
        final Double key = Double.valueOf(value);
        return computeIfAbsent(key, givenKey -> Integer.valueOf(function.applyAsInt(value)))
                .intValue();
    }

}
