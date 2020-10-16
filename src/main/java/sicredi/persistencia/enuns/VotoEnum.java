package sicredi.persistencia.enuns;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public enum VotoEnum {

	NAO(0),
	SIM(1);
	
	@Getter
	private Integer voto;
	
	private static Map<Integer, VotoEnum> votos = new HashMap<Integer, VotoEnum>();
	
	static {
		
		for(VotoEnum voto : VotoEnum.values() ) {
			votos.put(voto.getVoto(), voto);
		}
	}
	
	private VotoEnum(Integer voto) {
		this.voto = voto;
	}
	
	public static VotoEnum obterVotoPor(Integer id) {
		
		return votos.containsKey(id)
					? votos.get(id)
					: null;
	}
}
