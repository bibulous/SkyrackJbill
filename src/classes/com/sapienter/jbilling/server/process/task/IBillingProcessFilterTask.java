package com.sapienter.jbilling.server.process.task;

import org.hibernate.ScrollableResults;

public interface IBillingProcessFilterTask {

	public ScrollableResults findUsersToProcess(Integer entityId);
}
