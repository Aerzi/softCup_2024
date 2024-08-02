package com.example.backend.event;

import com.example.backend.model.entity.UserEventLog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @version 1.0.0
 * @description: The type User event.
 * @author feixia0g
 * @date 2024/8/2 16:05
 */
@Getter
public class UserEvent extends ApplicationEvent {
    /**
     * -- GETTER --
     *  Gets user event log.
     *
     * @return the user event log
     */
    private final UserEventLog userEventLog;

    /**
     * Instantiates a new User event.
     *
     * @param userEventLog the user event log
     */
    public UserEvent(final UserEventLog userEventLog) {
        super(userEventLog);
        this.userEventLog = userEventLog;
    }

}
