package ba.unsa.etf.rpr.tutorijal02;

import java.util.Objects;

public class Interval {

    double pocetak, kraj;
    boolean pocetakPripada, krajPripada;
    public Interval(double pocetna, double krajnja, boolean p, boolean k) {
        if(pocetna > krajnja) throw new IllegalArgumentException ("Pocetna tacka je veca od krajnje");
        pocetak = pocetna;
        kraj = krajnja;
        pocetakPripada = p;
        krajPripada = k;
    }
    public Interval() {
        pocetak = 0;
        kraj = 0;
        pocetakPripada = false;
        krajPripada = false;
    }
    public boolean isNull() {
        if (pocetak == kraj == pocetakPripada == krajPripada && pocetak == 0) return true;
        return false;
    }
    public boolean isIn(double tacka) {
        if((tacka > pocetak && tacka < kraj) || (pocetakPripada && tacka == pocetak) || (krajPripada && tacka == kraj))
            return true;
        return false;
    }
    public Interval intersect(Interval interval) {
        if(interval.kraj < this.pocetak || this.kraj < interval.pocetak) return new Interval(); //nema presjeka
        if(interval.kraj == this.pocetak && (!interval.krajPripada || !this.pocetakPripada)) return new Interval(); //neka od moguceg presjeka ne pripada
        if (this.kraj == interval.pocetak && (!this.krajPripada || !interval.pocetakPripada)) return new Interval(); //neka od moguceg presjeka ne pripada
        if(interval.kraj == this.pocetak && interval.krajPripada && this.pocetakPripada) return new Interval(interval.kraj, interval.kraj, true, true); //presjek jedna tacka
        if (this.kraj == interval.pocetak && this.krajPripada && interval.pocetakPripada) return new Interval(interval.pocetak, interval.pocetak, true, true); //presjek jedna tacka
        //nije se desio return pa trazimo interval presjeka
        if(interval.pocetak == this.pocetak) {
            if (interval.kraj == this.kraj) return new Interval(interval.pocetak, interval.kraj, (interval.pocetakPripada && this.pocetakPripada), (interval.krajPripada && this.krajPripada));
            else if (interval.kraj < this.kraj) return new Interval(interval.pocetak, interval.kraj, (interval.pocetakPripada && this.pocetakPripada), interval.krajPripada);
            else if (this.kraj < interval.kraj) return new Interval(interval.pocetak, interval.kraj, (interval.pocetakPripada && this.pocetakPripada), interval.krajPripada);
        }
        if(interval.kraj == this.kraj) {
            if(interval.pocetak > this.pocetak) return new Interval(interval.pocetak, interval.kraj, interval.pocetakPripada, (interval.krajPripada && this.krajPripada));
            else if(this.pocetak > interval.pocetak) return new Interval(this.pocetak, interval.kraj, this.pocetakPripada, (interval.krajPripada && this.krajPripada));
        }
        double p=0, k=0;
        boolean pp=false, kp=false;
        if(interval.pocetak > this.pocetak) { p = interval.pocetak; pp = interval.pocetakPripada; }
        else { p = this.pocetak; pp = this.pocetakPripada; }
        if(interval.kraj < this.kraj) { k = interval.kraj; kp = interval.krajPripada; }
        else { k = this.kraj; kp = this.krajPripada; }
        return new Interval(p, k, pp, kp);
    }
    public static Interval intersect(Interval prvi, Interval drugi) {
        return  prvi.intersect(drugi);
        /*
        if(interval.kraj < drugi.pocetak || drugi.kraj < interval.pocetak) return new Interval(); //nema presjeka
        if(interval.kraj == drugi.pocetak && (!interval.krajPripada || !drugi.pocetakPripada)) return new Interval(); //neka od moguceg presjeka ne pripada
        if (drugi.kraj == interval.pocetak && (!drugi.krajPripada || !interval.pocetakPripada)) return new Interval(); //neka od moguceg presjeka ne pripada
        if(interval.kraj == drugi.pocetak && interval.krajPripada && drugi.pocetakPripada) return new Interval(interval.kraj, interval.kraj, true, true); //presjek jedna tacka
        if (drugi.kraj == interval.pocetak && drugi.krajPripada && interval.pocetakPripada) return new Interval(interval.pocetak, interval.pocetak, true, true); //presjek jedna tacka
        //nije se desio return pa trazimo interval presjeka
        if(interval.pocetak == drugi.pocetak) {
            if (interval.kraj == drugi.kraj) return new Interval(interval.pocetak, interval.kraj, (interval.pocetakPripada && drugi.pocetakPripada), (interval.krajPripada && drugi.krajPripada));
            else if (interval.kraj < drugi.kraj) return new Interval(interval.pocetak, interval.kraj, (interval.pocetakPripada && drugi.pocetakPripada), interval.krajPripada);
            else if (drugi.kraj < interval.kraj) return new Interval(interval.pocetak, interval.kraj, (interval.pocetakPripada && drugi.pocetakPripada), interval.krajPripada);
        }
        if(interval.kraj == drugi.kraj) {
            if(interval.pocetak > drugi.pocetak) return new Interval(interval.pocetak, interval.kraj, interval.pocetakPripada, (interval.krajPripada && drugi.krajPripada));
            else if(drugi.pocetak > interval.pocetak) return new Interval(drugi.pocetak, interval.kraj, drugi.pocetakPripada, (interval.krajPripada && drugi.krajPripada));
        }
        double p=0, k=0;
        boolean pp=false, kp=false;
        if(interval.pocetak > drugi.pocetak) { p = interval.pocetak; pp = interval.pocetakPripada; }
        else { p = drugi.pocetak; pp = drugi.pocetakPripada; }
        if(interval.kraj < drugi.kraj) { k = interval.kraj; kp = interval.krajPripada; }
        else { k = drugi.kraj; kp = drugi.krajPripada; }
        return new Interval(p, k, pp, kp); */
    }
    @Override
    public String toString() {
        if(this.isNull()) return "()";
        String vrati = "";
        if(pocetakPripada) vrati = vrati + "[";
        else vrati = vrati + "(";
        vrati = vrati + String.valueOf(pocetak) + "," + String.valueOf(kraj);
        if(krajPripada) vrati = vrati + "]";
        else vrati = vrati + ")";
        return vrati;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return Double.compare(interval.pocetak, pocetak) == 0 &&
                Double.compare(interval.kraj, kraj) == 0 &&
                pocetakPripada == interval.pocetakPripada &&
                krajPripada == interval.krajPripada;
    }

}
