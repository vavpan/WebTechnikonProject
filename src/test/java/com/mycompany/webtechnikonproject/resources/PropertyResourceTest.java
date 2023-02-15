/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.model.Property;
import com.mycompany.webtechnikonproject.services.OwnerService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author ADMIN
 */
@ExtendWith(MockitoExtension.class)
public class PropertyResourceTest {

    @Mock
    public OwnerService mockOwnerService;

    @InjectMocks
    public PropertyResource propertyResourceUnderTest;

    @Test
    public void testGetProperty() {
        PropertyDto propertyDto = new PropertyDto(new Property(0, 9, "address", "yearOfConstruction", PropertyType.APARTMENT_BUILDING));
        RestApiResult<PropertyDto> expectedResult = new RestApiResult<>(propertyDto, 0, "description");
        when(mockOwnerService.getProperty(0)).thenReturn(expectedResult);

        RestApiResult<PropertyDto> result = propertyResourceUnderTest.getProperty(0);

        assertEquals(expectedResult.getData(), result.getData());
        assertEquals(expectedResult.getDescription(), result.getDescription());
    }

    @Test
    public void testGetAllProperties() {
        final List<Property> properties = Arrays.asList(
                new Property(1, 9, "address1", "yearOfConstruction1", PropertyType.APARTMENT_BUILDING),
                new Property(2, 8, "address2", "yearOfConstruction2", PropertyType.DETACHED_HOUSE)
        );
        final List<PropertyDto> expectedResult = properties.stream().map(PropertyDto::new).collect(Collectors.toList());
        when(mockOwnerService.getAllProperties()).thenReturn(expectedResult);

        final List<PropertyDto> result = propertyResourceUnderTest.getAllProperties();

        assertThat(result).isEqualTo(expectedResult);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedResult);
    }

    @Test
    public void testCheckE9() {
        int e9 = 12345;
        when(mockOwnerService.checkE9(e9)).thenReturn(true);

        boolean result = propertyResourceUnderTest.checkE9(e9);

        assertTrue(result);
        verify(mockOwnerService).checkE9(e9);
    }

    @Test
    public void testReturnPropertiesByOwnerVat() {

        PropertyDto property1 = new PropertyDto(new Property(1, 9, "address", "yearOfConstruction", PropertyType.APARTMENT_BUILDING));
        PropertyDto property2 = new PropertyDto(new Property(2, 9, "address", "yearOfConstruction", PropertyType.MAISONETTE));

        List<PropertyDto> expectedProperties = Arrays.asList(property1, property2);

        when(mockOwnerService.getPropertiesByOwnerVat(9)).thenReturn(expectedProperties);

        List<PropertyDto> actualProperties = propertyResourceUnderTest.returnPropertiesByOwnervat(9);

        assertEquals(expectedProperties, actualProperties);

    }

    @Test
    public void testReturnOwnerByE9() {

        PropertyDto property = new PropertyDto(new Property(1, 1234, "address", "yearOfConstruction", PropertyType.APARTMENT_BUILDING));

        when(mockOwnerService.getPropertyByE9(1234)).thenReturn(property);

        PropertyDto propertyDto = propertyResourceUnderTest.returnOwnerByE9(1234);

        assertEquals(propertyDto, property);
    }

    @Test
    public void testUpdateYearOfConstruction() {
        Property property = new Property(0, 9, "address", "oldYearOfConstruction", PropertyType.APARTMENT_BUILDING);
        PropertyDto expectedResult = new PropertyDto(property);
        when(mockOwnerService.updateYearOfConstruction(0, "newYearOfConstruction")).thenReturn(expectedResult);
        PropertyDto result = propertyResourceUnderTest.updateYearOfConstruction(0, "newYearOfConstruction");
        assertEquals(expectedResult, result);
        verify(mockOwnerService).updateYearOfConstruction(0, "newYearOfConstruction");
    }

    public void testUpdatePropertyType() {
        Property property = new Property(0, 2, "address", "yearOfConstruction", PropertyType.DETACHED_HOUSE);
        PropertyDto expectedResult = new PropertyDto(property);
        when(mockOwnerService.updatePropertyType(0, PropertyType.MAISONETTE)).thenReturn(expectedResult);
        PropertyDto result = propertyResourceUnderTest.updatePropertyType(0, PropertyType.MAISONETTE);
        assertEquals(expectedResult, result);
        verify(mockOwnerService).updatePropertyType(0, PropertyType.MAISONETTE);
    }

    @Test
    public void testDeleteProperty() {
        when(mockOwnerService.deleteProperty(0)).thenReturn(true);
        boolean result = propertyResourceUnderTest.deleteProperty(0);
        assertTrue(result);
        verify(mockOwnerService).deleteProperty(0);
    }

}
