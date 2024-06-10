package bg.tihomir.hobby.service;

import bg.tihomir.hobby.handler.FailToDeleteException;
import bg.tihomir.hobby.handler.NotFoundException;
import bg.tihomir.hobby.model.dto.view.AboViewModel;
import bg.tihomir.hobby.model.entity.Abo;
import bg.tihomir.hobby.repository.AboRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AboService {

    private final AboRepository aboRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public AboService(AboRepository aboRepository,
                      UserService userService,
                      ModelMapper modelMapper) {
        this.aboRepository = aboRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public List<Abo> saveAbos(List<Abo> inCart) {
        return this.aboRepository.saveAll(inCart);
    }

    public void updateAbosWithEntries(List<Abo> abos) {
        this.aboRepository.saveAll(abos);
    }

    public List<AboViewModel> getAbosPerBusiness() {
        Long id = this.userService.findCurrentUserBusinessOwner().getId();
        List<Abo> abos = this.aboRepository.findAllByBusinessOwnerId(id);

        return abos.stream().map(abo -> this.modelMapper.map(abo, AboViewModel.class)).toList();
    }


    public List<AboViewModel> getUserAbos(Long id) {
        List<Abo> byClientId = this.aboRepository.findByClientId(id);
        return byClientId.stream().map(abo -> modelMapper.map(abo, AboViewModel.class)).toList();
    }

    public void deleteAbo(Long id) {
        Optional<Abo>byId = this.aboRepository.findById(id);
        if (byId.isPresent()) {
            this.aboRepository.deleteById(id);
        }else {
            throw new NotFoundException("Abo does not exist");
        }

    }

    public void findExcistingAbosWithHobbyId(Long id) {
        List<Abo> byHobbyId = this.aboRepository.findByHobbyId(id);
        if (byHobbyId.size() > 0) {
            throw new FailToDeleteException("Can not delete hobby. There are existing Abos for this offer.");

        }
    }
}
