package bg.tihomir.hobby.service;

import bg.tihomir.hobby.model.entity.Entry;
import bg.tihomir.hobby.repository.EntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryService {

    private final EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public List<Entry> saveAboEntries(List<Entry> aboEntries) {
        return this.entryRepository.saveAll(aboEntries);
    }
}
