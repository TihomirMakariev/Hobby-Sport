package bg.tihomir.hobby.service;

import bg.tihomir.hobby.model.dto.binding.TestDTO;
import bg.tihomir.hobby.repository.TestRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TestService {


    private final ModelMapper modelMapper;
    private final TestRepository testRepository;
    private final HobbyService hobbyService;

    public TestService(ModelMapper modelMapper,
                       TestRepository testRepository,
                       HobbyService hobbyService) {
        this.modelMapper = modelMapper;
        this.testRepository = testRepository;
        this.hobbyService = hobbyService;
    }

    public void saveTest(TestDTO testDTO) {

    }
}
