package ASKy.Backend.service;

import ASKy.Backend.dto.request.ExpertFilterRequest;
import ASKy.Backend.dto.response.ExpertResponse;
import ASKy.Backend.model.Expert;
import ASKy.Backend.repository.ExpertRepository;
import ASKy.Backend.specification.ExpertSpecification;
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

    public ExpertService(ExpertRepository expertRepository, ModelMapper modelMapper) {
        this.expertRepository = expertRepository;
        this.modelMapper = modelMapper;
    }

    public List<ExpertResponse> searchExperts(ExpertFilterRequest filters) {
        Specification<Expert> specification = ExpertSpecification.byFilters(filters);
        List<Expert> experts = expertRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "averageRating"));

        return experts.stream()
                .map(expert -> modelMapper.map(expert, ExpertResponse.class))
                .collect(Collectors.toList());
    }
}
