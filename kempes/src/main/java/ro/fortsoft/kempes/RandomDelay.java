/*
 * Copyright 2014 FortSoft
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package ro.fortsoft.kempes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author Decebal Suiu
 */
public class RandomDelay {

    private static final Logger log = LoggerFactory.getLogger(RandomDelay.class);

    private static Random random = new Random();

    public static void delay(int min, int max) {
        int value = getValue(min, max);
        log.debug("Sleep '{}' for {} seconds", Thread.currentThread(), value);
        try {
            Thread.sleep(value * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static int getValue(int min, int max) {
        int range = max - min + 1;

        return random.nextInt(range) + min;
    }

}
