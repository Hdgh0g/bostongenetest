package com.hdgh0g.bostongenetest.tasks;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.Translation;
import com.hdgh0g.bostongenetest.repositories.AppealRepository;
import com.hdgh0g.bostongenetest.service.TranslationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppealTranslationTask extends AbstractTranslationTask<Appeal> {

    private final AppealRepository appealRepository;

    public AppealTranslationTask(TranslationService translationService,
                                 AppealRepository appealRepository) {
        super(translationService);
        this.appealRepository = appealRepository;
    }

    @Override
    protected List<Appeal> getEntitiesToTranslate() {
        return appealRepository.findAllWhereTranslationIsNull();
    }

    @Override
    protected String getTextToTranslate(Appeal entity) {
        return entity.getText();
    }

    @Override
    protected Optional<String> detectSourceLanguage(String sourceText) {
        return translationService.detectLanguage(sourceText);
    }

    @Override
    protected Optional<String> detectTargetLanguage(Appeal entity) {
        return Optional.of(DEFAULT_LANGUAGE);
    }

    @Override
    protected void saveTranslation(Appeal entity, Translation translation) {
        entity.setTranslation(translation);
        appealRepository.save(entity);
    }
}
