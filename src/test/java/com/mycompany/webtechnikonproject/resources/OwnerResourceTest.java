package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.services.OwnerService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

public class OwnerResourceTest {

    @Mock
    private OwnerService mockOwnerService;

    @InjectMocks
    private OwnerResource ownerResourceUnderTest;

    @Test
    void testGetOwner() {
        // Setup
        // Configure OwnerService.getOwner(...).
        final RestApiResult<PropertyOwnerDto> expectedResult = new RestApiResult<>(new PropertyOwnerDto(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password",
                        "role")), 0, "description");
        when(mockOwnerService.getOwner(0)).thenReturn(expectedResult);

        // Run the test
        final RestApiResult<PropertyOwnerDto> result = ownerResourceUnderTest.getOwner(0);

        // Verify the results
        assertEquals(expectedResult.getData(), result.getData());
        assertEquals(expectedResult.getDescription(), result.getDescription());
    }
}
