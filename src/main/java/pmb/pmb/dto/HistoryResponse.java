package pmb.pmb.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HistoryResponse {
String displayName;
String accountReferenceTransaction;
LocalDateTime date;
double soldAccount;
}
