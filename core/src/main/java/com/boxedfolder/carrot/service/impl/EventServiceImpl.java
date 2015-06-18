package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.repository.EventRepository;
import com.boxedfolder.carrot.service.EventService;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class EventServiceImpl extends CrudServiceImpl<Event, EventRepository> implements EventService {
}