package com.hdgh0g.bostongenetest.tasks;

import com.hdgh0g.bostongenetest.domain.Appeal;
import com.hdgh0g.bostongenetest.domain.AppealAnswer;
import com.hdgh0g.bostongenetest.domain.Translation;
import com.hdgh0g.bostongenetest.repositories.AppealAnswerRepository;
import com.hdgh0g.bostongenetest.service.TranslationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppealAnswerTranslationTask extends AbstractTranslationTask<AppealAnswer> {

    private final AppealAnswerRepository appealAnswerRepository;

    public AppealAnswerTranslationTask(TranslationService translationService,
                                       AppealAnswerRepository appealAnswerRepository) {
        super(translationService);
        this.appealAnswerRepository = appealAnswerRepository;
    }

    @Override
    protected List<AppealAnswer> getEntitiesToTranslate() {
        return appealAnswerRepository.findAllWhereTranslationIsNull();
    }

    @Override
    protected String getTextToTranslate(AppealAnswer entity) {
        return entity.getText();
    }

    @Override
    protected Optional<String> detectSourceLanguage(String sourceText) {
        return Optional.of(DEFAULT_LANGUAGE);
    }

    @Override
    protected Optional<String> detectTargetLanguage(AppealAnswer entity) {
        return Optional.ofNullable(entity.getAppeal()).map(Appeal::getTranslation).map(Translation::getSourceLanguage);
    }

    @Override
    protected void saveTranslation(AppealAnswer entity, Translation translation) {
        appealAnswerRepository.save(entity);
    }
}
