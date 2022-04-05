package rundeck.services.audit

import com.dtolabs.rundeck.core.audit.AuditEvent
import com.dtolabs.rundeck.plugins.audit.AuditEventListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public final class AuditEventDispatcher {

    private static final Logger LOG = LoggerFactory.getLogger(AuditEventDispatcher.class)
    
    private AuditEventDispatcher() {}

    /**
     * Dispatch an event to a listener.
     */
    static dispatchToListener(AuditEvent event, AuditEventListener listener) {

        try {

            // Call general callback
            listener.onEvent(event)

            // Call specific callbacks
            if (ResourceTypes.USER.equals(event.resourceInfo.type)) {
                switch (event.actionType) {
                    case ActionTypes.LOGIN_SUCCESS:
                        listener.onLoginSuccess(event)
                        break

                    case ActionTypes.LOGIN_FAILED:
                        listener.onLoginFailed(event)
                        break

                    case ActionTypes.LOGOUT:
                        listener.onLogout(event)
                        break
                }
            } else if (ResourceTypes.PROJECT.equals(event.resourceInfo.type)) {
                switch (event.actionType) {
                    case ActionTypes.VIEW:
                        listener.onProjectView(event)
                        break
                }
            }

        }
        catch (Exception e) {
            LOG.error("Error dispatching event to handler plugin: " + e.getMessage(), e)
        }
    }
    
}
