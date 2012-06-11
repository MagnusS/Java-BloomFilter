/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.skjegstad.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A (very) simple benchmark to evaluate the performance of the Bloom filter class.
 *
 * @author Magnus Skjegstad
 */
public class BloomfilterBenchmark {
    static int elementCount = 50000; // Number of elements to test

    public static void printStat(long start, long end) {
        double diff = (end - start) / 1000.0;
        System.out.println(diff + "s, " + (elementCount / diff) + " elements/s");
    }

    public static void main(String[] argv) {
        

        final Random r = new Random();

        // Generate elements first
        List<String> existingElements = new ArrayList(elementCount);
        for (int i = 0; i < elementCount; i++) {
            byte[] b = new byte[200];
            r.nextBytes(b);
            existingElements.add(new String(b));
        }
        
        List<String> nonExistingElements = new ArrayList(elementCount);
        for (int i = 0; i < elementCount; i++) {
            byte[] b = new byte[200];
            r.nextBytes(b);
            nonExistingElements.add(new String(b));
        }

        BloomFilter<String> bf = new BloomFilter<String>(0.001, elementCount);

        System.out.println("Testing " + elementCount + " elements");
        System.out.println("k is " + bf.getK());

        // Add elements
        System.out.print("add(): ");
        long start_add = System.currentTimeMillis();
        for (int i = 0; i < elementCount; i++) {
            bf.add(existingElements.get(i));
        }
        long end_add = System.currentTimeMillis();
        printStat(start_add, end_add);

        // Check for existing elements with contains()
        System.out.print("contains(), existing: ");
        long start_contains = System.currentTimeMillis();
        for (int i = 0; i < elementCount; i++) {
            bf.contains(existingElements.get(i));
        }
        long end_contains = System.currentTimeMillis();
        printStat(start_contains, end_contains);

        // Check for existing elements with containsAll()
        System.out.print("containsAll(), existing: ");
        long start_containsAll = System.currentTimeMillis();
        for (int i = 0; i < elementCount; i++) {
            bf.contains(existingElements.get(i));
        }
        long end_containsAll = System.currentTimeMillis();
        printStat(start_containsAll, end_containsAll);

        // Check for nonexisting elements with contains()
        System.out.print("contains(), nonexisting: ");
        long start_ncontains = System.currentTimeMillis();
        for (int i = 0; i < elementCount; i++) {
            bf.contains(nonExistingElements.get(i));
        }
        long end_ncontains = System.currentTimeMillis();
        printStat(start_ncontains, end_ncontains);

        // Check for nonexisting elements with containsAll()
        System.out.print("containsAll(), nonexisting: ");
        long start_ncontainsAll = System.currentTimeMillis();
        for (int i = 0; i < elementCount; i++) {
            bf.contains(nonExistingElements.get(i));
        }
        long end_ncontainsAll = System.currentTimeMillis();
        printStat(start_ncontainsAll, end_ncontainsAll);

    }
}
