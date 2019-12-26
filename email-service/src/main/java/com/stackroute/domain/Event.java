package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private int id;
    private String eventName;
    private String scheduledDate;
    private String publishDate;
    private String eventDescription;
    private String eventVenue;
    private String eventPosterUrl;
    private String[] invitations;
    private String invitationMessage;
    private String[] attending;
    private String[] maybeAttending;
    private String[] notAttending;
}

