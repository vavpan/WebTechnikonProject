package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerResourceTest {

    @Mock
    private OwnerService mockOwnerService;

    @InjectMocks
    private OwnerResource ownerResourceUnderTest;

    @Test
    void testGetOwner() {
        // Setup
        // Configure OwnerService.getOwner(...).
        final RestApiResult<PropertyOwnerDto> propertyOwnerDtoRestApiResult = new RestApiResult<>(new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role")), 0, "description");
        when(mockOwnerService.getOwner(0)).thenReturn(propertyOwnerDtoRestApiResult);

        // Run the test
        final RestApiResult<PropertyOwnerDto> result = ownerResourceUnderTest.getOwner(0);

        // Verify the results
    }

    @Test
    void testGetOwnerByVat() {
        // Setup
        // Configure OwnerService.getOwnerByVat(...).
        final RestApiResult<PropertyOwnerDto> propertyOwnerDtoRestApiResult = new RestApiResult<>(new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role")), 0, "description");
        when(mockOwnerService.getOwnerByVat(0)).thenReturn(propertyOwnerDtoRestApiResult);

        // Run the test
        final RestApiResult<PropertyOwnerDto> result = ownerResourceUnderTest.getOwnerByVat(0);

        // Verify the results
    }

    @Test
    void testGetOwnerByEmail() {
        // Setup
        // Configure OwnerService.getOwnerByEmail(...).
        final RestApiResult<PropertyOwnerDto> propertyOwnerDtoRestApiResult = new RestApiResult<>(new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role")), 0, "description");
        when(mockOwnerService.getOwnerByEmail("email")).thenReturn(propertyOwnerDtoRestApiResult);

        // Run the test
        final RestApiResult<PropertyOwnerDto> result = ownerResourceUnderTest.getOwnerByEmail("email");

        // Verify the results
    }

    @Test
    void testGetAllOwners() {
        // Setup
        final List<PropertyOwnerDto> expectedResult = List.of(new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role")));

        // Configure OwnerService.getAllOwners(...).
        final List<PropertyOwnerDto> propertyOwnerDtos = List.of(new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role")));
        when(mockOwnerService.getAllOwners()).thenReturn(propertyOwnerDtos);

        // Run the test
        final List<PropertyOwnerDto> result = ownerResourceUnderTest.getAllOwners();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllOwners_OwnerServiceReturnsNoItems() {
        // Setup
        when(mockOwnerService.getAllOwners()).thenReturn(Collections.emptyList());

        // Run the test
        final List<PropertyOwnerDto> result = ownerResourceUnderTest.getAllOwners();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testUpdateOwner() {
        // Setup
        final PropertyOwnerDto ownerDto = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));

        // Configure OwnerService.updateOwner(...).
        final RestApiResult<PropertyOwnerDto> propertyOwnerDtoRestApiResult = new RestApiResult<>(new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role")), 0, "description");
        when(mockOwnerService.updateOwner(new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role")), 0)).thenReturn(propertyOwnerDtoRestApiResult);

        // Run the test
        final RestApiResult<PropertyOwnerDto> result = ownerResourceUnderTest.updateOwner(ownerDto, 0);

        // Verify the results
    }

    @Test
    void testUpdateAddress() {
        // Setup
        final PropertyOwnerDto expectedResult = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));

        // Configure OwnerService.updateAddress(...).
        final PropertyOwnerDto propertyOwnerDto = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));
        when(mockOwnerService.updateAddress(0, "newAddress")).thenReturn(propertyOwnerDto);

        // Run the test
        final PropertyOwnerDto result = ownerResourceUnderTest.updateAddress(0, "newAddress");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdateEmail() {
        // Setup
        final PropertyOwnerDto expectedResult = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));

        // Configure OwnerService.updateEmail(...).
        final PropertyOwnerDto propertyOwnerDto = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));
        when(mockOwnerService.updateEmail(0, "email")).thenReturn(propertyOwnerDto);

        // Run the test
        final PropertyOwnerDto result = ownerResourceUnderTest.updateEmail(0, "email");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdateName() {
        // Setup
        final PropertyOwnerDto expectedResult = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));

        // Configure OwnerService.updateName(...).
        final PropertyOwnerDto propertyOwnerDto = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));
        when(mockOwnerService.updateName(0, "name")).thenReturn(propertyOwnerDto);

        // Run the test
        final PropertyOwnerDto result = ownerResourceUnderTest.updateName(0, "name");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdateSurname() {
        // Setup
        final PropertyOwnerDto expectedResult = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));

        // Configure OwnerService.updateSurname(...).
        final PropertyOwnerDto propertyOwnerDto = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));
        when(mockOwnerService.updateSurname(0, "surname")).thenReturn(propertyOwnerDto);

        // Run the test
        final PropertyOwnerDto result = ownerResourceUnderTest.updateSurname(0, "surname");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdatePassword() {
        // Setup
        final PropertyOwnerDto expectedResult = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));

        // Configure OwnerService.updatePassword(...).
        final PropertyOwnerDto propertyOwnerDto = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));
        when(mockOwnerService.updatePassword(0, "password")).thenReturn(propertyOwnerDto);

        // Run the test
        final PropertyOwnerDto result = ownerResourceUnderTest.updatePassword(0, "password");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdatePhoneNumber() {
        // Setup
        final PropertyOwnerDto expectedResult = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));

        // Configure OwnerService.updatePhoneNumber(...).
        final PropertyOwnerDto propertyOwnerDto = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));
        when(mockOwnerService.updatePhoneNumber(0, "phoneNumber")).thenReturn(propertyOwnerDto);

        // Run the test
        final PropertyOwnerDto result = ownerResourceUnderTest.updatePhoneNumber(0, "phoneNumber");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdateUsername() {
        // Setup
        final PropertyOwnerDto expectedResult = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));

        // Configure OwnerService.updateUsername(...).
        final PropertyOwnerDto propertyOwnerDto = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));
        when(mockOwnerService.updateUsername(0, "username")).thenReturn(propertyOwnerDto);

        // Run the test
        final PropertyOwnerDto result = ownerResourceUnderTest.updateUsername(0, "username");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdateVat() {
        // Setup
        final PropertyOwnerDto expectedResult = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));

        // Configure OwnerService.updateVat(...).
        final PropertyOwnerDto propertyOwnerDto = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));
        when(mockOwnerService.updateVat(0, 0)).thenReturn(propertyOwnerDto);

        // Run the test
        final PropertyOwnerDto result = ownerResourceUnderTest.updateVat(0, "vatString");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testCreateNewOwner() {
        // Setup
        final PropertyOwnerDto owner = new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role"));

        // Run the test
        ownerResourceUnderTest.createNewOwner(owner);

        // Verify the results
        verify(mockOwnerService).createPropertyOwner(new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role")));
    }

    @Test
    void testDeleteOwner() {
        // Setup
        when(mockOwnerService.deletePropertyOwner(0)).thenReturn(false);

        // Run the test
        final boolean result = ownerResourceUnderTest.deleteOwner(0);

        // Verify the results
        assertFalse(result);
    }
}
