package ua.nure.ponomarev.service;


import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;
/**
 * @author Bogdan_Ponamarev.
 */
public class TokenSet implements Set<Token> {
    private CopyOnWriteArraySet<Token> tokens;

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor((Runnable r) ->
    {
        Thread th = new Thread(r);
        th.setDaemon(true);
        return th;
    });
    public TokenSet(int defaultMinutesDelay){
        tokens = new CopyOnWriteArraySet<>();
        scheduler.scheduleAtFixedRate(() -> {
                long currentMinutes = System.currentTimeMillis()/60000;
                for (Token t : tokens) {
                    if (!t.isAlive(currentMinutes)) {
                        tokens.remove(t);
                    }
                }

        }, 2, defaultMinutesDelay, TimeUnit.MINUTES);
    }
    @Override
    public int size() {
        return tokens.size();
    }

    @Override
    public boolean isEmpty() {
        return tokens.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return tokens.contains(o);
    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }

    @Override
    public Object[] toArray() {
        return tokens.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return tokens.toArray(a);
    }


    @Override
    public boolean add(Token token) {
        if(tokens.contains(token)){
            tokens.remove(token);
        }
        return tokens.add(token);
    }

    @Override
    public boolean remove(Object o) {
        return tokens.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return tokens.containsAll(c);
    }


    @Override
    public boolean addAll(Collection<? extends Token> c) {
        return tokens.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return tokens.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return tokens.removeAll(c);
    }

    @Override
    public void clear() {
        tokens.clear();
    }
}
