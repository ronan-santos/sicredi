package sicredi.view;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import sicredi.negocio.VotacaoNegocio;
import sicredi.negocio.dto.ResultadoVotacaoDto;
import sicredi.negocio.dto.VotoDto;

@Api("votacao")
@Path("votacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VotacaoView {
	
	@Inject
	private VotacaoNegocio negocio;

	
	@POST
	@Path("voto")
	@ApiOperation(value = "Computa o voto do associado", notes = "São aceitos como votos: 1 - NÃO , 2 - SIM")
	@ApiResponses(value = @ApiResponse(code = 200, message = "Voto computado com sucesso"))
	public Response receberVoto(VotoDto voto) {
		
		negocio.receberVoto(VotoDto.criarVoto(voto));
		
		return Response.ok().build();
	}
	
	@GET
	@Path("resultado/{idPauta}")
	@ApiOperation(value = "Retorna o resultado da votação da pauta",response = ResultadoVotacaoDto.class)
	@ApiResponses(value = @ApiResponse(code = 200, message = "Votação apurada com sucesso"))
	public Response obterResultadoPor(@PathParam("idPauta") Long idPauta ) {
		
		return Response.ok(negocio.obterResultadorVotacaoPor(idPauta)).build();
	}

}
