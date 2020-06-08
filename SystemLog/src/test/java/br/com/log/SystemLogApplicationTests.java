package br.com.log;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.log.controle.LogControle;
import com.log.servico.LogServico;

@SpringBootTest
public class SystemLogApplicationTests {

	MockMvc mockMvc;

	@InjectMocks
	private LogControle logControle;
	
	@Mock LogServico logServico;
	
	@Before
	public void setup() throws Exception {
		 MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(logControle).build();
	}

	@Test
	public void verificarVersao() throws Exception {
		mockMvc.perform(get("/api/versao/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void consultarLog() throws Exception {
		 mockMvc.perform(
	                get("/api/consultar-log/")
	                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void consultarLogLista() throws Exception {
		 mockMvc.perform(
	                get("/api/consultar-log-list/").param("idDados", "1")
	                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void salvarUser() throws Exception {
		 mockMvc.perform(
	                post("/api/salvar-user/")
	                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void salvarLogTexto() throws Exception {
		 mockMvc.perform(
	                post("/api/salvar-log-texto/").content("teste")
	                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void excluirLog() throws Exception {
		 mockMvc.perform(
	                delete("/api/excluir-log/1")
	                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void baixarArquivoLog() throws Exception {
		 byte[] anyBytes = new byte[]{};
		 mockMvc.perform(
	                get("/api/baixar-arquivo-log/82")
	                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		 .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
         .andExpect(content().bytes(anyBytes));
	}
	
	

}
