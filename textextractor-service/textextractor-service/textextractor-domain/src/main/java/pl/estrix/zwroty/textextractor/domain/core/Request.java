package pl.estrix.zwroty.textextractor.domain.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.estrix.zwroty.textextractor.common.domain.vo.RequestId;

@AllArgsConstructor
@Builder
@Getter
public class Request {

    private RequestId id;
}
