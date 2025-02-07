/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2022 Adobe
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
package com.adobe.cq.forms.core.components.models.form;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonValue;

public interface FormatConstraint {

    public enum Format {
        DATE("date");

        private String value;

        Format(String value) {
            this.value = value;
        }

        public static Format fromString(String value) {
            for (Format format : Format.values()) {
                if (StringUtils.equals(value, format.value)) {
                    return format;
                }
            }
            return null;
        }

        /**
         * Returns the string value of this enum constant.
         *
         * @return the string value of this enum constant
         */
        public String getValue() {
            return value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

    /**
     * Returns the format of the form field as specified in the json schema specification(for example, date, binary etc)
     *
     * @return the format of the form field
     * @since com.adobe.cq.forms.core.components.models.form 0.0.1
     */
    @Nullable
    String getFormat();
}
