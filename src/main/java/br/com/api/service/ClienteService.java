package br.com.api.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.api.domain.Cliente;
import br.com.api.domain.dto.ClienteDTO;
import br.com.api.repositories.ClienteRepository;
import br.com.api.service.exceptions.DataIntegrityException;
import br.com.api.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> 
		new ObjectNotFoundException("Cliente não encontrado! Id:" + id.toString()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		
		return repo.save(obj);
	}
	
	public Cliente update(Cliente obj) {
		Cliente updateCli = find(obj.getId());
		updateCliente(updateCli,obj);
		return repo.save(updateCli);
		
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
			
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir o cliente");
		}
	}
	private void updateCliente(Cliente newCliente, Cliente obj) {
		
		newCliente.setEmail(obj.getEmail());
		newCliente.setNome(obj.getNome());
		newCliente.setTelefone(obj.getTelefone());
		
	}
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getTelefone());
	}
	
	public Cliente fromDTOUpdate(ClienteDTO objDto) {
		return new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getTelefone());
	}
}
