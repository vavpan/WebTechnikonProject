package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.services.OwnerService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Collectors;
import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OwnerResourceTest {

    @Mock
    public OwnerService mockOwnerService;

    @InjectMocks
    public OwnerResource ownerResourceUnderTest;

    @Test
    public void testGetOwner() {
        final RestApiResult<PropertyOwnerDto> expectedResult = new RestApiResult<>(new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role")), 0, "description");
        when(mockOwnerService.getOwner(0)).thenReturn(expectedResult);

        final RestApiResult<PropertyOwnerDto> result = ownerResourceUnderTest.getOwner(0);

      
        assertEquals(expectedResult.getData(), result.getData());
        assertEquals(expectedResult.getDescription(), result.getDescription());
    }

    @Test
    public void testGetAllOwners() {
        final List<PropertyOwner> owners = Arrays.asList(
                new PropertyOwner(1, "name1", "surname1", "address1", "phoneNumber1", "email1", "username1", "password1", "role1"),
                new PropertyOwner(2, "name2", "surname2", "address2", "phoneNumber2", "email2", "username2", "password2", "role2")
        );
        final List<PropertyOwnerDto> expectedResult = owners.stream().map(PropertyOwnerDto::new).collect(Collectors.toList());
        when(mockOwnerService.getAllOwners()).thenReturn(expectedResult);

        final List<PropertyOwnerDto> result = ownerResourceUnderTest.getAllOwners();

        assertThat(result).isEqualTo(expectedResult);
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedResult);

    }

    @Test
    public void testCreateNewOwner() {
        final PropertyOwnerDto ownerDto = new PropertyOwnerDto(new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password", "role"));
        final PropertyOwnerDto expectedResult = new PropertyOwnerDto(new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password", "role"));
        when(mockOwnerService.createPropertyOwner(ownerDto)).thenReturn(expectedResult);

        final PropertyOwnerDto result = ownerResourceUnderTest.createNewOwner(ownerDto);

        assertEquals(expectedResult.getId(), result.getId());

    }

    @Test
    public void testUpdateOwner() {
        final int ownerId = 1;
        final PropertyOwnerDto ownerDto = new PropertyOwnerDto(new PropertyOwner(ownerId, "name", "surname", "address", "phoneNumber", "email", "username", "password", "role"));
        final RestApiResult<PropertyOwnerDto> expectedResult = new RestApiResult<>(ownerDto, 0, "successful");
        when(mockOwnerService.updateOwner(ownerDto, ownerId)).thenReturn(expectedResult);

        final RestApiResult<PropertyOwnerDto> result = ownerResourceUnderTest.updateOwner(ownerDto, ownerId);

        assertEquals(expectedResult.getData(), result.getData());
        assertEquals(expectedResult.getErrorCode(), result.getErrorCode());
        assertEquals(expectedResult.getDescription(), result.getDescription());
    }

    @Test
    public void testUpdateEmail() {
        final int ownerId = 1;
        final String email = "test@email.gr";
        final PropertyOwnerDto expectedResult = new PropertyOwnerDto(new PropertyOwner(ownerId, "name", "surname", "address", "phoneNumber", email, "username", "password", "role"));
        when(mockOwnerService.updateEmail(ownerId, email)).thenReturn(expectedResult);

        final PropertyOwnerDto result = ownerResourceUnderTest.updateEmail(ownerId, email);

        assertEquals(expectedResult.getId(), result.getId());
        assertEquals(expectedResult.getName(), result.getName());
        assertEquals(expectedResult.getSurname(), result.getSurname());
        assertEquals(expectedResult.getAddress(), result.getAddress());
        assertEquals(expectedResult.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(expectedResult.getEmail(), result.getEmail());
        assertEquals(expectedResult.getUsername(), result.getUsername());
        assertEquals(expectedResult.getPassword(), result.getPassword());
        assertEquals(expectedResult.getRole(), result.getRole());
    }

    @Test
    public void testDeleteOwner() {
        when(mockOwnerService.deletePropertyOwner(0)).thenReturn(true);
        boolean result = ownerResourceUnderTest.deleteOwner(0);
        assertTrue(result);
        verify(mockOwnerService).deletePropertyOwner(0);
    }

}
