package shared.model;

import java.io.Serializable;

public enum UserPermission implements Serializable {
    CAN_START,
    CAN_STOP,
    CAN_RESTART,
    CAN_PRINT,
    CAN_READ_QUEUE,
    CAN_EDIT_QUEUE,
    CAN_READ_CONFIG,
    CAN_WRITE_CONFIG,
    CAN_READ_STATUS
}
