package NeuroActivity.letstalk.controller;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ThinkControllerTest {

    ThinkController thinkController = new ThinkController();

    @Test
    public void getListOfThinks() {
        assertEquals(thinkController.getListOfThinks(),"index");
    }

    @Test
    public void getOneThink() {
    }

    @Test
    public void createThink() {
    }

    @Test
    public void changeThink() {
    }

    @Test
    public void deleteThink() {
    }
}