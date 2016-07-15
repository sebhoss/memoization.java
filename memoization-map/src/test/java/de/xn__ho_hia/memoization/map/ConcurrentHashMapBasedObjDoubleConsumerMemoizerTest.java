package de.xn__ho_hia.memoization.map;

import java.util.HashMap;
import java.util.Map;
import java.util.function.ObjDoubleConsumer;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import de.xn__ho_hia.memoization.shared.ObjDoubleFunction;
import de.xn__ho_hia.quality.suppression.CompilerWarnings;

/**
 *
 */
@SuppressWarnings({ CompilerWarnings.NLS, CompilerWarnings.STATIC_METHOD })
public class ConcurrentHashMapBasedObjDoubleConsumerMemoizerTest {

    /**
    *
    */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
    *
    */
    @Test
    public void shouldAcceptPreComputedValuesKeyFunctionAndConsumer() {
        // given
        final Map<String, String> precomputedValues = new HashMap<>();
        final ObjDoubleFunction<String, String> keyFunction = (first, second) -> first + second;
        final ObjDoubleConsumer<String> consumer = (first, second) -> System.out.println(first + second);

        // when
        final ConcurrentHashMapBasedObjDoubleConsumerMemoizer<String, String> memoizer = new ConcurrentHashMapBasedObjDoubleConsumerMemoizer<>(
                precomputedValues, keyFunction, consumer);

        // then
        Assert.assertNotNull("Memoizer is NULL", memoizer);
    }

    /**
    *
    */
    @Test
    @SuppressWarnings(CompilerWarnings.UNUSED)
    public void shouldRequireNonNullPreComputedValues() {
        // given
        final Map<String, String> precomputedValues = null;
        final ObjDoubleFunction<String, String> keyFunction = (first, second) -> first + second;
        final ObjDoubleConsumer<String> consumer = (first, second) -> System.out.println(first + second);

        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Provide an empty map instead of NULL in case you don't have any precomputed values.");

        // then
        new ConcurrentHashMapBasedObjDoubleConsumerMemoizer<>(precomputedValues, keyFunction, consumer);
    }

    /**
    *
    */
    @Test
    @SuppressWarnings(CompilerWarnings.UNUSED)
    public void shouldRequireNonNullKeyFunction() {
        // given
        final Map<String, String> precomputedValues = new HashMap<>();
        final ObjDoubleFunction<String, String> keyFunction = null;
        final ObjDoubleConsumer<String> consumer = (first, second) -> System.out.println(first + second);

        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(
                "Provide a key function, might just be 'MemoizationDefaults.objDoubleConsumerHashCodeKeyFunction()'.");

        // then
        new ConcurrentHashMapBasedObjDoubleConsumerMemoizer<>(precomputedValues, keyFunction, consumer);
    }

    /**
    *
    */
    @Test
    @SuppressWarnings(CompilerWarnings.UNUSED)
    public void shouldRequireNonNullConsumer() {
        // given
        final Map<String, String> precomputedValues = new HashMap<>();
        final ObjDoubleFunction<String, String> keyFunction = (first, second) -> first + second;
        final ObjDoubleConsumer<String> consumer = null;

        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Cannot memoize a NULL Consumer - provide an actual Consumer to fix this.");

        // then
        new ConcurrentHashMapBasedObjDoubleConsumerMemoizer<>(precomputedValues, keyFunction, consumer);
    }

    /**
    *
    */
    @Test
    public void shouldMemoizeConsumer() {
        // given
        final Map<String, String> precomputedValues = new HashMap<>();
        final ObjDoubleFunction<String, String> keyFunction = (first, second) -> first + second;
        final ObjDoubleConsumer<String> consumer = (first, second) -> System.out.println(first + second);

        // when
        final ConcurrentHashMapBasedObjDoubleConsumerMemoizer<String, String> memoizer = new ConcurrentHashMapBasedObjDoubleConsumerMemoizer<>(
                precomputedValues, keyFunction, consumer);

        // then
        memoizer.accept("test", 123.456D);
    }

    /**
    *
    */
    @Test
    public void shouldUseSetCacheKeyAndValue() {
        // given
        final Map<String, String> precomputedValues = new HashMap<>();
        final ObjDoubleFunction<String, String> keyFunction = (first, second) -> first + second;
        final ObjDoubleConsumer<String> consumer = (first, second) -> System.out.println(first + second);

        // when
        final ConcurrentHashMapBasedObjDoubleConsumerMemoizer<String, String> memoizer = new ConcurrentHashMapBasedObjDoubleConsumerMemoizer<>(
                precomputedValues, keyFunction, consumer);

        // then
        memoizer.accept("test", 123.456D);
        Assert.assertFalse("Cache is still empty after memoization", memoizer.viewCacheForTest().isEmpty());
        Assert.assertEquals("Memoization key does not match expectations", "test123.456",
                memoizer.viewCacheForTest().keySet().iterator().next());
        Assert.assertEquals("Memoization value does not match expectations", "test123.456",
                memoizer.viewCacheForTest().values().iterator().next());
    }

    /**
    *
    */
    @Test
    @SuppressWarnings(CompilerWarnings.UNCHECKED)
    public void shouldUseCallWrappedConsumer() {
        // given
        final Map<String, String> precomputedValues = new HashMap<>();
        final ObjDoubleFunction<String, String> keyFunction = (first, second) -> first + second;
        final ObjDoubleConsumer<String> consumer = Mockito.mock(ObjDoubleConsumer.class);

        // when
        final ConcurrentHashMapBasedObjDoubleConsumerMemoizer<String, String> memoizer = new ConcurrentHashMapBasedObjDoubleConsumerMemoizer<>(
                precomputedValues, keyFunction, consumer);

        // then
        memoizer.accept("test", 123.456D);
        Mockito.verify(consumer).accept("test", 123.456D);
    }

}
