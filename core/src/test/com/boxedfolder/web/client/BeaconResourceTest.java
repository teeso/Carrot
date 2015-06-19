package com.boxedfolder.web.client;

import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.util.View;
import com.boxedfolder.carrot.service.BeaconService;
import com.boxedfolder.carrot.web.client.BeaconResouce;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class BeaconResourceTest {
    @Mock
    private BeaconService service;
    private MockMvc restUserMockMvc;
    private List<Beacon> testData;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BeaconResouce resource = new BeaconResouce();
        resource.setService(service);
        restUserMockMvc = MockMvcBuilders.standaloneSetup(resource).build();

        mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(View.Client.class));

        // Create 3 beacons
        testData = new ArrayList<Beacon>();
        Beacon beacon = new Beacon();
        beacon.setName("Beacon 1");
        beacon.setMajor(1);
        beacon.setMinor(2);
        beacon.setUuid(UUID.fromString("de305d54-75b4-431b-adb2-eb6b9e546014"));
        testData.add(beacon);

        beacon = new Beacon();
        beacon.setName("Beacon 2");
        beacon.setMajor(1);
        beacon.setMinor(2);
        beacon.setUuid(UUID.fromString("de305d54-75b4-431b-adb2-eb6b9e546011"));
        testData.add(beacon);
        testData.add(beacon);

        beacon = new Beacon();
        beacon.setName("Beacon 3");
        beacon.setMajor(1);
        beacon.setMinor(2);
        beacon.setUuid(UUID.fromString("de305d54-75b4-431b-adb2-eb6b9e546012"));
        testData.add(beacon);
        testData.add(beacon);
    }

    @Test
    public void testGetAllBeacons() throws Exception {
        String value = mapper.writeValueAsString(testData);

        when(service.findAll()).thenReturn(testData);
        restUserMockMvc.perform(get("/client/beacons")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(value));
    }

    @Test
    public void testAddBeacon() throws Exception {
        Beacon beacon = testData.get(0);
        String value = mapper.writeValueAsString(beacon);
        given(service.save((Beacon)notNull())).willReturn(beacon);
        restUserMockMvc.perform(post("/client/beacons")
                .content(value)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(value));
    }

    @Test
    public void testDeleteBeacon() throws Exception {
        restUserMockMvc.perform(delete("/client/beacons/0"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSingleBeacon() throws Exception {
        Beacon beacon = testData.get(0);
        given(service.find(1L)).willReturn(beacon);
        String value = mapper.writeValueAsString(beacon);
        restUserMockMvc.perform(get("/client/beacons/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(value));
    }
}