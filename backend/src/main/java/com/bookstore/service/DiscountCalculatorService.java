package com.bookstore.service;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class DiscountCalculatorService {
    private static final double BASE_BOOK_PRICE = 50.0;
    private static final Map<Integer, Double> DISCOUNT_RATES = Map.of(
            1,1.00,
            2,0.95,
            3,0.90,
            4,0.80,
            5,0.75
    );
    public double calculateBestTotalPrice(List<Long> bookIdList){
        if(bookIdList == null || bookIdList.isEmpty()){
            return  0.0;
        }
        Map<Long,Integer> bookCounts = calculateBookFrequencies(bookIdList);
        return findOptimalPriceForCounts(bookCounts);
    }
    private Map<Long, Integer> calculateBookFrequencies(List<Long> bookIdList){
        Map<Long, Integer> counts = new HashMap<>();
        for(Long bookId: bookIdList){
            counts.put(bookId,counts.getOrDefault(bookId,0)+1);
        }
        return counts;
    }

    private double findOptimalPriceForCounts(Map<Long,Integer> bookCounts){
        List<Integer> nonZeroFrequencies = bookCounts.values().stream()
                .filter(count -> count>0)
                .toList();
        if(nonZeroFrequencies.isEmpty())
        {
            return 0.0;
        }
        int uniqueBookTypesCount = nonZeroFrequencies.size();
        double minTotalPrice = Double.MAX_VALUE;

        for (int setSize = 1;setSize <= uniqueBookTypesCount;setSize++){
            Map<Long, Integer> remainingCounts = decrementBookCounts(bookCounts,setSize);
            double currentSetPrice = setSize * BASE_BOOK_PRICE *DISCOUNT_RATES.get(setSize);
            double remainingPrice = findOptimalPriceForCounts(remainingCounts);
            minTotalPrice = Math.min(minTotalPrice,currentSetPrice + remainingPrice);
        }
        return minTotalPrice;

    }
    private Map<Long, Integer> decrementBookCounts(Map<Long,Integer> currentCounts,int itemsToRemove){
        Map<Long,Integer> updatedCounts = new HashMap<>(currentCounts);
        List<Long> availableBookIds = updatedCounts.entrySet().stream()
                .filter(entry -> entry.getValue()>0)
                .map(Map.Entry::getKey)
                .limit(itemsToRemove)
                .toList();
        for (Long bookId : availableBookIds){
            updatedCounts.put(bookId, updatedCounts.get(bookId)-1);
        }
        return updatedCounts;

    }

}
