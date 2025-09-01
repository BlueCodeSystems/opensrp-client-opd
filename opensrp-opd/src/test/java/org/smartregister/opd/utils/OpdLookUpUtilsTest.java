package org.smartregister.opd.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.robolectric.util.ReflectionHelpers;
import org.smartregister.Context;
import org.smartregister.commonregistry.CommonPersonObject;
import org.smartregister.opd.BuildConfig;
import org.smartregister.opd.OpdLibrary;
import org.smartregister.opd.activity.BaseOpdFormActivity;
import org.smartregister.opd.configuration.OpdConfiguration;
import org.smartregister.opd.pojo.OpdMetadata;
import org.smartregister.repository.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OpdLibrary.class, OpdUtils.class, android.text.TextUtils.class})
public class OpdLookUpUtilsTest {

    @Mock
    private OpdLibrary opdLibrary;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        // Mock TextUtils.isEmpty()
        PowerMockito.mockStatic(android.text.TextUtils.class);
        PowerMockito.when(android.text.TextUtils.isEmpty(Mockito.anyString())).thenAnswer(invocation -> {
            String str = invocation.getArgument(0);
            return str == null || str.trim().isEmpty();
        });
    }

    @Test
    public void testLookUpQueryShouldReturnQueryString() throws Exception {
        PowerMockito.mockStatic(OpdLibrary.class);
        OpdMetadata opdMetadata = new OpdMetadata(OpdConstants.JSON_FORM_KEY.NAME
                , OpdDbConstants.KEY.TABLE
                , OpdConstants.EventType.OPD_REGISTRATION
                , OpdConstants.EventType.UPDATE_OPD_REGISTRATION
                , OpdConstants.CONFIG
                , BaseOpdFormActivity.class
                , null
                , true);
        
        // Fix the lookUpQueryForOpdClient that was initialized with null table name
        String correctLookUpQuery = String.format("select id as _id, %s, %s, %s, %s, %s, %s, %s, national_id from ec_client where [condition] ",
                OpdConstants.KEY.RELATIONALID, OpdConstants.KEY.FIRST_NAME,
                OpdConstants.KEY.LAST_NAME, OpdConstants.KEY.GENDER,
                OpdConstants.KEY.DOB, OpdConstants.KEY.BASE_ENTITY_ID, 
                OpdDbConstants.KEY.OPENSRP_ID);
        opdMetadata.setLookUpQueryForOpdClient(correctLookUpQuery);
        
        OpdConfiguration op = Mockito.mock(OpdConfiguration.class);
        PowerMockito.when(op.getOpdMetadata()).thenReturn(opdMetadata);
        PowerMockito.when(opdLibrary.getOpdConfiguration()).thenReturn(op);
        PowerMockito.when(OpdLibrary.getInstance()).thenReturn(opdLibrary);
        Map<String, String> entityMap = new HashMap<>();
        entityMap.put("first_name", "Ann");
        PowerMockito.mockStatic(OpdUtils.class);
        PowerMockito.when(OpdUtils.metadata()).thenReturn(opdMetadata);
        String result = Whitebox.invokeMethod(OpdLookUpUtils.class, "lookUpQuery", entityMap);
        Assert.assertNotNull(result);
        Assert.assertTrue("Expected result to contain 'from ec_client' but was: " + result, result.contains("from ec_client"));
        Assert.assertTrue("Expected result to end with 'first_name Like '%Ann%' ;' but was: " + result, result.endsWith("first_name Like '%Ann%' ;"));
    }

    @Test
    public void testGetMainConditionStringWhenEntityMapIsEmpty() throws Exception {
        Map<String, String> entityMap = new HashMap<>();
        String result = Whitebox.invokeMethod(OpdLookUpUtils.class, "getMainConditionString", entityMap);
        Assert.assertEquals("", result);
    }

    @Test
    public void testGetMainConditionStringWhenEntityMapIsWithValue() throws Exception {
        String firstName = "first_name";
        String lastName = "last_name";
        String bht_id = "bht_mid";
        String national_id = "national_id";
        Map<String, String> entityMap = new HashMap<>();
        entityMap.put(firstName, "");
        entityMap.put(lastName, "");
        entityMap.put(bht_id, "");
        entityMap.put(national_id, "");
        String result = Whitebox.invokeMethod(OpdLookUpUtils.class, "getMainConditionString", entityMap);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testClientLookUpWhenContextIsNull() throws Exception {
        Map<String, String> entityLookUp = new HashMap<>();
        List<CommonPersonObject> result = Whitebox.invokeMethod(OpdLookUpUtils.class, "clientLookUp", (Object) null, entityLookUp);
        List<CommonPersonObject> expectedResult = new ArrayList<>();
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testClientLookUpWhenMapIsEmpty() throws Exception {
        Map<String, String> entityLookUp = new HashMap<>();
        List<CommonPersonObject> result = Whitebox.invokeMethod(OpdLookUpUtils.class, "clientLookUp", PowerMockito.mock(Context.class), entityLookUp);
        List<CommonPersonObject> expectedResult = new ArrayList<>();
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testClientLookUpWhenMapIsNotEmptyAndContextIsNotNullWithTable() throws Exception {
        OpdLibrary.init(PowerMockito.mock(Context.class), PowerMockito.mock(Repository.class), PowerMockito.mock(OpdConfiguration.class),
                BuildConfig.VERSION_CODE, 1);
        Map<String, String> entityLookUp = new HashMap<>();
        entityLookUp.put("first_name", "");
        List<CommonPersonObject> result = Whitebox.invokeMethod(OpdLookUpUtils.class, "clientLookUp", PowerMockito.mock(Context.class), entityLookUp);
        List<CommonPersonObject> expectedResult = new ArrayList<>();
        Assert.assertEquals(expectedResult, result);
    }

    @After
    public void tearDown() {
        ReflectionHelpers.setStaticField(OpdLibrary.class, "instance", null);
    }

}
