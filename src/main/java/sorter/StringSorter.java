package sorter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StringSorter implements FileSorter {
    private List<List<String>> fileContents;
    private SortOrder sortOrder;
    private boolean invalidDataDetected;

    public StringSorter(List<List<String>> fileContents, SortOrder sortOrder) {
        this.fileContents = fileContents;
        this.sortOrder = sortOrder;
        this.invalidDataDetected = false;
    }

    @Override
    public List<String> sort() {

        List<String> mergedList = new ArrayList<>();

        for (List<String> list : fileContents) {
            for (String data : list) {
                if (isValidData(data)) {
                    mergedList.add(data);
                } else {
                    invalidDataDetected = true;
                }
            }
        }

        if (invalidDataDetected) {
            System.out.println("Внимание! Данные, которые не соответствовали заявленным критериям сортировки, были пропущены.");
        }

        mergeSort(mergedList, sortOrder);
        return mergedList;
    }

    private boolean isValidData(String data) {
        return data.matches("^[a-zA-Zа-яА-Я\\s]*$");
    }

    private void mergeSort(List<String> list, SortOrder order) {
        if (list.size() <= 1) {
            return;
        }

        int mid = list.size() / 2;
        List<String> left = new ArrayList<>(list.subList(0, mid));
        List<String> right = new ArrayList<>(list.subList(mid, list.size()));

        mergeSort(left, order);
        mergeSort(right, order);

        merge(list, left, right, order);
    }

    private void merge(List<String> list, List<String> left, List<String> right, SortOrder order) {
        int leftIndex = 0;
        int rightIndex = 0;
        int listIndex = 0;

        Comparator<String> comparator = (order == SortOrder.ASCENDING) ? Comparator.naturalOrder() : Comparator.reverseOrder();

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (comparator.compare(left.get(leftIndex), right.get(rightIndex)) <= 0) {
                list.set(listIndex, left.get(leftIndex));
                leftIndex++;
            } else {
                list.set(listIndex, right.get(rightIndex));
                rightIndex++;
            }
            listIndex++;
        }

        while (leftIndex < left.size()) {
            list.set(listIndex, left.get(leftIndex));
            leftIndex++;
            listIndex++;
        }

        while (rightIndex < right.size()) {
            list.set(listIndex, right.get(rightIndex));
            rightIndex++;
            listIndex++;
        }
    }
}