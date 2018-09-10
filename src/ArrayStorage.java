import javax.sound.midi.Soundbank;

class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int resumeAmount = 0;

    public void save(Resume resume) {

        storage[resumeAmount] = resume;
        resumeAmount++;  //является одновременно и счётчиком кол-ва резюме, и адресом первого пустого эл-та массива
    }

    public Resume get(String uuid) {
        for (int i = 0; i < resumeAmount; i++) {
            if (storage[i].getUuid() == uuid) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String delId) {
        int elemToCopy;
        for (int i = 0; i < resumeAmount; i++) {
            if (storage[i].getUuid() == delId) {
                elemToCopy = storage.length - i - 1;
                System.arraycopy(storage, i + 1, storage, i, elemToCopy);
                storage[storage.length - 1] = null;
                System.out.println("Резюме с uuid номер " + delId + " успешно удалено!");
                resumeAmount--;
                return;
            }
        }
        System.out.println("Резюме с номером " + delId + " не найдено!");
    }

    public int size() {
        return resumeAmount;
    }

    public Resume[] getAll() {
        Resume allResumes[] = new Resume[resumeAmount];
        for (int i = 0; i < resumeAmount; i++) {
            allResumes[i] = storage[i];
        }
        return allResumes;
    }

    public void clear() {
        for (int i = 0; i < resumeAmount; i++) {
            storage[i] = null;
        }
        resumeAmount = 0;
    }
}
