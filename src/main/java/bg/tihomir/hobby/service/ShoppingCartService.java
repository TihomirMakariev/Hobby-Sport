package bg.tihomir.hobby.service;

import bg.tihomir.hobby.model.entity.Abo;
import bg.tihomir.hobby.model.entity.AppClient;
import bg.tihomir.hobby.model.entity.Entry;
import bg.tihomir.hobby.model.entity.HobbyEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ShoppingCartService {

    private final UserService userService;
    private final AboService aboService;
    private final EntryService entryService;

    private List<Abo> inCart = new ArrayList<>();

    public ShoppingCartService(UserService userService,
                               AboService aboService,
                               EntryService entryService) {
        this.userService = userService;
        this.aboService = aboService;
        this.entryService = entryService;
    }

    public void addAboToCart(HobbyEntity hobby) {
        Abo abo = new Abo();
        AppClient currentAppClient = this.userService.findCurrentUserAppClient();
        abo.setClientId(currentAppClient.getId());
        abo.setBusinessOwnerId(hobby.getBusinessOwner().getId());
        abo.setHobbyId(hobby.getId());
        abo.setName(hobby.getName());
        abo.setImgUrl(hobby.getImgUrlMain());
        //TODO create method "FullName"
        abo.setClientName(currentAppClient.getFullName());

        /*BigDecimal price = hobby.getPrice().multiply(new BigDecimal(5));
        price = price.add(price.multiply(new BigDecimal("0.1")));
        price = price.setScale(2, RoundingMode.HALF_EVEN);
        abo.setAboPrice(price);*/

        BigDecimal price = hobby.getPrice();
        abo.setAboPrice(price);



        if (!inCart.contains(abo)) {
            inCart.add(abo);
        }
    }

    public void removeProductFromCart(Long hobbyId) {
        Iterator<Abo> iterator = inCart.iterator();
        while (iterator.hasNext()) {
            Abo abo = iterator.next();
            if (abo.getHobbyId().equals(hobbyId)) {
                iterator.remove();
                break;
            }
        }
    }

    public List<Abo> getAbosInCart(){
        return inCart;
    }

    public BigDecimal getTotal() {
        return inCart.stream()
                .map(Abo::getAboPrice).reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public void checkout() {
        List<Abo> abos = this.aboService.saveAbos(inCart);
        /*abos.forEach(this::fillEntries);
        this.aboService.updateAbosWithEntries(abos);*/
        inCart.clear();
    }

    private void fillEntries(Abo abo) {
        List<Entry> aboEntries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Entry entry = new Entry();
            entry.setAbo(abo);
            aboEntries.add(entry);
        }
        List<Entry> entries = this.entryService.saveAboEntries(aboEntries);
        abo.setEntries(entries);
    }

}
