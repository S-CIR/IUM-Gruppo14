package com.example.myapplication.WikiPlants;

import java.util.ArrayList;

public class WikiPlant {

    private String name;
    private String type;
    private String seeding;
    private String description;

    public WikiPlant() {}

    public WikiPlant(String name, String type, String seeding, String description) {
        this.name = name;
        this.type = type;
        this.seeding = seeding;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeeding() {
        return seeding;
    }

    public void setSeeding(String seeding) {
        this.seeding = seeding;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void doRetrieveInfo (ArrayList<WikiPlant> list){

        list.add(new WikiPlant("Basilico","Aromatica","Feb-Mar","Il basilico (Ocimum basilicum, L., 1753) è una pianta erbacea annuale, appartenente alla famiglia delle Lamiaceae, normalmente coltivata come pianta aromatica.\n" +
                "\n" +
                "Originario dell'India, il basilico è utilizzato tipicamente nella cucina italiana e nelle cucine asiatiche in Taiwan, Thailandia, Vietnam, Cambogia e Laos, per via del marcato profumo delle sue foglie, che a seconda della varietà può essere più o meno dolce o pungente"));
        list.add(new WikiPlant("Rosmarino","Aromatica","Mar-Apr","Il rosmarino (Salvia rosmarinus Schleid.) è una pianta perenne aromatica appartenente alla famiglia delle Lamiaceae.\n" +
                "\n" +
                "Originario dell'area mediterranea dove cresce nelle zone litoranee, garighe, macchia mediterranea, dirupi sassosi e assolati dell'entroterra, dal livello del mare fino alla zona collinare, ma si è acclimatato anche nella zona dei laghi prealpini e nella Pianura Padana nei luoghi sassosi e collinari."));
        list.add(new WikiPlant("Peperoncino","Aromatica","Gen-Apr","Il peperoncino è il nome comune dato alla bacca ottenuta da alcune varietà piccanti del genere di piante Capsicum utilizzata principalmente come condimento.\n" +
                "\n" +
                "Pur provenendo dal medesimo genere del peperone, il peperoncino se ne differenzia poiché contiene la capsaicina, un composto chimico presente in diverse concentrazioni a seconda delle varietà, responsabile della piccantezza della bacca. La piccantezza di un peperoncino si indica utilizzando la scala di Scoville, che misura la quantità di capsaicina contenuta (16 unità di Scoville sono equivalenti a una parte di capsaicina per milione)"));
        list.add(new WikiPlant("Sedano","Aromatica","Apr-Ott","Il sedano (Apium graveolens L.) è una specie erbacea biennale appartenente alla famiglia delle Apiaceae, originaria della zona mediterranea e conosciuto come pianta medicinale fin dai tempi di Omero."));
        list.add(new WikiPlant("Prezzemolo","Aromatica","Feb-Mag","Il prezzemolo (Petroselinum crispum (Mill.) Fuss, 900) è una pianta biennale della famiglia delle Apiaceae, originaria delle zone mediterranee. Cresce spontaneamente nei boschi e nei prati delle zone a clima temperato; teme infatti il freddo intenso."));

        list.add(new WikiPlant("Melo","Frutto","/","Il melo (Malus domestica Borkh., 1803) è una pianta da frutto appartenente alla famiglia delle Rosacee. È una delle più diffuse piante da frutto coltivate."));
        list.add(new WikiPlant("Pero","Frutto","/","Il Pero (Pyrus L., 1753) è un genere di piante appartenente alla famiglia delle Rosaceae, comprendente specie arboree e arbustive con fioritura delicata e variamente colorata."));
        list.add(new WikiPlant("Arancio","Frutto","/","L'arancio (Citrus sinensis (L.) Osbeck, 1765) è un albero da frutto appartenente alla famiglia delle Rutacee, il cui frutto è l'arancia (detta nell'uso corrente anche \"arancio\", come l'albero), talora chiamata arancia dolce per distinguerla dall'arancia amara. È un antico ibrido, risultato di un incrocio di oltre 4000 anni fa tra il pomelo e il mandarino."));
        list.add(new WikiPlant("Mandarino","Frutto","/","Il mandarino (Citrus reticulata Blanco, 1837) è un albero da frutto appartenente alla famiglia delle Rutaceae.\n" +
                "È uno dei tre agrumi originali del genere Citrus assieme al cedro ed al pomelo. Nel 2014, un lavoro scientifico ha chiarito la complessa sistematica degli agrumi definendo come tutti gli agrumi derivino da tre sole specie (mandarino, pomelo e cedro). Il mandarino ha certo acquistato importanza storica, in quanto si tratta dell'unico frutto dolce tra i tre originali. Da incroci con il mandarino si sono sviluppati quasi tutti gli agrumi che oggi conosciamo (es. limone, lime, arance)."));
        list.add(new WikiPlant("Limone","Frutto","/","Il limone (Citrus limon (L.) Osbeck) è un albero da frutto appartenente alla famiglia delle Rutaceae. Il nome comune limone si può riferire tanto alla pianta quanto al suo frutto.\n" +
                "\n" +
                "Secondo degli studi genetici[2], il limone è un ibrido e deriva dall'incrocio tra l'arancio amaro e il cedro"));

        list.add(new WikiPlant("Pomodoro","Vegetali","Feb-Mar","Il pomodoro (Solanum lycopersicum, L. 1753) - identificato secondo il Codice Internazionale di Nomenclatura Botanica (ICBN) Lycopersicon esculentum (L.) Karsten ex Farw. (cfr. classificazione botanica), della famiglia delle Solanaceae, è una pianta annuale. Le sue bacche, dal caratteristico colore rosso, sono largamente utilizzate in ambito alimentare in molti Paesi del mondo."));
        list.add(new WikiPlant("Insalata","Vegetali","Apr-Giu","L'insalata indica genericamente diversi vegetali commestibili le cui foglie si possono mangiare crude e variamente condite; il termine indica anche preparazioni composte da più ingredienti, solitamente da verdure in foglia crude più o meno sminuzzate, caratterizzate dal fatto di essere condite a crudo e mescolate solitamente con una miscela di sale da cucina e olio d'oliva e/o aceti o spremuta di limone e/o altri ingredienti opzionali come pepe, origano, aglio, prezzemolo, rafano e altre spezie aromatiche fresche o essiccate ma anche con lievito alimentare e salse crude varie, come quella di semi di senape."));
        list.add(new WikiPlant("Peperone","Vegetali","Feb","Il peperone è il nome comune dato alla bacca ottenuta da alcune varietà della specie Capsicum annuum e utilizzata come verdura.\n" +
                "\n" +
                "Le varietà di peperoni producono frutti di differenti forme (allungata, conica, a prisma o a globo), superfici (liscia o costoluta), colori (verde, rosso, giallo, arancione, violetto) e sapore (acre o dolce). I peperoni vengono consumati sia freschi (crudi o cotti), sia in alcuni casi essiccati (ad esempio il peperone crusco)."));
        list.add(new WikiPlant("Melenzana","Vegetali","Gen-Feb","La melanzana (Solanum melongena L.) è una pianta angiosperma dicotiledone appartenente alla famiglia delle Solanaceae, coltivata per il frutto commestibile.\n" +
                "\n" +
                "Originariamente fu domesticata a partire da specie selvatiche di Solanum incanum, probabilmente con due percorsi indipendenti: uno in Asia meridionale e uno in Asia orientale."));
        list.add(new WikiPlant("Zucchino","Vegetali","Apr-Mag","La zucchina o zucchino (Cucurbita pepo L.) è una specie di pianta appartenente alla famiglia Cucurbitaceae i cui frutti sono utilizzati immaturi.\n" +
                "\n" +
                "È una pianta annuale con fusto erbaceo flessibile strisciante o rampicante, gracile. Venne importata in Europa intorno al 1500 dopo la scoperta dell'America."));

    }
}
