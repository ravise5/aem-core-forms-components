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
package com.adobe.cq.forms.core.components.it.service;

import com.adobe.forms.common.service.*;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


@Component(
        service={DataProvider.class, CustomAFPrefillService.class},
        immediate = true
)
public class CustomAFPrefillService implements DataProvider {
    @Reference
    private DataManager dataManager;

    private Logger logger = LoggerFactory.getLogger(CustomAFPrefillService.class);

    /* (non-Javadoc)
     * @see com.adobe.forms.common.service.DataProviderBase#getServiceDescription()
     */
    @Override
    public String getServiceDescription() {
        return "Core Custom Pre-fill Service - SOC864";
    }

    /* (non-Javadoc)
     * @see com.adobe.forms.common.service.DataProviderBase#getServiceName()
     */
    @Override
    public String getServiceName() {
        return "Core Custom Pre-fill Service - SOC864";
    }

    /* (non-Javadoc)
     * @see com.adobe.forms.common.service.DataProvider#getPrefillData(com.adobe.forms.common.service.DataOptions)
     */
    @Override
    public PrefillData getPrefillData(DataOptions dataOptions) throws FormsException {
        logger.info("Invoking AF custom prefill service");
        InputStream dataInputStream = null;
        ContentType contentType = dataOptions.getContentType();
        Map<String, Object> extras = dataOptions.getExtras();
        String data = null;
        List<FileAttachmentWrapper> fileAttachmentWrappers = null;
        PrefillData prefillData = null;
        Map<String, String> customContext = null;

        String jsonData = "{\r\n   \"SOC864\": {\r\n      \"preparer\": null,\r\n      \"name\": \"SOC864\",\r\n      \"generateLargeFontPdfAfterSubmission\": false,\r\n      \"externalId\": \"provider=0123456789\",\r\n      \"language\": \"en\",\r\n      \"id\": \"e7c737d2-8c0f-48b9-9aba-14572bfcf4ea\",\r\n      \"status\": [\r\n         \"IN_PROGRESS\"\r\n      ],\r\n      \"data\": {\r\n         \"socialWorkerIdentifier\": \"WO01\",\r\n         \"caseNumber\": \"1234567\",\r\n         \"countySocialServicesWorkerPhone\": \"(000) 555-1122\",\r\n         \"publicAuthorityPhone\": \"(000) 555-1212\",\r\n         \"countyIHSSSocialServicesOfficePhone\": \"(000) 555-1212\",\r\n         \"countySocialServicesWorkerName\": \"Kern County Worker\",\r\n         \"receipientFullName\": \"Daffy Duck\",\r\n         \"countyIHSSSocialServicesOfficeName\": \"Kern County IHSS\"\r\n      }\r\n   }\r\n}";
        String id = "e7c737d2-8c0f-48b9-9aba-14572bfcf4ea";

        if(extras != null && extras.containsKey(DataManager.UNIQUE_ID) && dataManager != null) {
            String dataKey = extras.get(DataManager.UNIQUE_ID).toString();
            if(StringUtils.equals(dataKey, id)) {
                data = jsonData;
            }
        }
        if(StringUtils.isNotBlank(data)) {
            dataInputStream = getDataInputStream(data);
            prefillData = new PrefillData(dataInputStream, contentType, fileAttachmentWrappers, customContext);
        }
        return prefillData;
    }


    private InputStream getDataInputStream(String data) {
        InputStream dataInputStream = null;
        try {
            dataInputStream = new ByteArrayInputStream(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("[Custom AF Prefill] Exception in custom af prefill service", e);
        }
        return dataInputStream;
    }
}
