package Itstime.planear.statusmessage.dto;

import Itstime.planear.statusmessage.domain.MessageType;

public record StatusCreateRequest(
        MessageType type,
        QnaCreateRequest qna
) {
    public record QnaCreateRequest(
            Long questionId,
            String answer
    ) {
    }
}
