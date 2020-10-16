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
import sicredi.negocio.PautaNegocio;
import sicredi.negocio.dto.PautaDto;

@Path("pautas")
@Api("pautas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PautaView {
	
	@Inject
	private PautaNegocio negocio;

	@GET
	@Path("{id}")
	@ApiOperation(value = "Retorna uma pauta pelo id", response = PautaDto.class)
	public Response obterPor(@PathParam("id") Long id ) {
		
		return Response.ok(PautaDto.toDto(negocio.obterPor(id))).build();
	}
	
	@GET
	@ApiOperation(value = "Retornas todas as pautas cadastradas", response = PautaDto.class)
	public Response obterTodas() {
	
		return Response.ok(negocio.obterTodas()).build();
	}
	
	@POST
	@ApiOperation(value = "Cadastra uma nova pauta", notes = "São campos obrigatórios: proponente, nome, dataInicioVotação. Obs.: a data deve ter o padão -> yyyy-MM-dd HH:mm:ss  ")
	@ApiResponses(value = @ApiResponse(code = 200, message = "Pauta cadastrada com sucesso"))
	public Response salvarPauta(PautaDto pauta ) {
		
		negocio.salvar(PautaDto.criarNovaPauta(pauta));
		
		return Response.ok().build();
	}
	
	@GET
	@Path("votacao")
	@ApiOperation(value = "Retorna as lista de pautas que estão em período de votação",response = PautaDto.class)
	public Response obterPautasEmVotacao() {
		
		return Response.ok(negocio.obterPautasEmVotacao()).build();
	}
}
