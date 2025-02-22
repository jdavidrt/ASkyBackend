package ASKy.Backend.service;

import ASKy.Backend.dto.request.ExpertFilterRequest;
import ASKy.Backend.dto.response.ExpertResponse;
import ASKy.Backend.model.Expert;
import ASKy.Backend.repository.ExpertRepository;
import ASKy.Backend.repository.UserRepository;
import ASKy.Backend.specification.ExpertSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpertService {

    private final ExpertRepository expertRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    public ExpertService(ExpertRepository expertRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.expertRepository = expertRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public List<ExpertResponse> searchExperts(ExpertFilterRequest filters) {
        Specification<Expert> specification = ExpertSpecification.byFilters(filters);
        List<Expert> experts = expertRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "averageRating"));

        return experts.stream()
                .map(expert -> modelMapper.map(expert, ExpertResponse.class))
                .collect(Collectors.toList());
    }

    public void applyExpertSanctions(Integer expertId) {
        Expert expert = (Expert) userRepository.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("Experto no encontrado"));

        if (expert.getAverageRating() < 2.5) { // 🔹 Example threshold for sanctions
            expert.setSanctioned(true);
            userRepository.save(expert);
        }
    }
}
