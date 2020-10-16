package sicredi.negocio.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CpfRetornoVo implements Serializable {


	private static final long serialVersionUID = -6576305847270720042L;
	private String status;
}
