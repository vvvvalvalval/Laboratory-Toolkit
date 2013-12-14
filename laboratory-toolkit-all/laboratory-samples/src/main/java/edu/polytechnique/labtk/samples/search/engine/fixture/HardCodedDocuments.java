/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.fixture;

import edu.polytechnique.labtk.samples.search.engine.equipment.Document;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class HardCodedDocuments {

    public static final Document ARTHUR_WIKIPEDIA = DocumentImpl.newDocumentBuilder()
            .addTextField("title", "King Arthur")
            .addTextField("paragraph", "King Arthur is a legendary British leader of the late 5th and early 6th centuries, who, according to medieval histories and romances, led the defence of Britain against Saxon invaders in the early 6th century. The details of Arthur's story are mainly composed of folklore and literary invention, and his historical existence is debated and disputed by modern historians.[2] The sparse historical background of Arthur is gleaned from various sources, including the Annales Cambriae, the Historia Brittonum, and the writings of Gildas. Arthur's name also occurs in early poetic sources such as Y Gododdin.[3]")
            .addTextField("paragraph", "The legendary Arthur developed as a figure of international interest largely through the popularity of Geoffrey of Monmouth's fanciful and imaginative 12th-century Historia Regum Britanniae (History of the Kings of Britain).[4] Some Welsh and Breton tales and poems relating the story of Arthur date from earlier than this work; in these works, Arthur appears either as a great warrior defending Britain from human and supernatural enemies or as a magical figure of folklore, sometimes associated with the Welsh Otherworld, Annwn.[5] How much of Geoffrey's Historia (completed in 1138) was adapted from such earlier sources, rather than invented by Geoffrey himself, is unknown.")
            .addTextField("paragraph", "Although the themes, events and characters of the Arthurian legend varied widely from text to text, and there is no one canonical version, Geoffrey's version of events often served as the starting point for later stories. Geoffrey depicted Arthur as a king of Britain who defeated the Saxons and established an empire over Britain, Ireland, Iceland, Norway and Gaul. Many elements and incidents that are now an integral part of the Arthurian story appear in Geoffrey's Historia, including Arthur's father Uther Pendragon, the wizard Merlin, Arthur's wife Guinevere, the sword Excalibur, Arthur's conception at Tintagel, his final battle against Mordred at Camlann and final rest in Avalon. The 12th-century French writer Chrétien de Troyes, who added Lancelot and the Holy Grail to the story, began the genre of Arthurian romance that became a significant strand of medieval literature. In these French stories, the narrative focus often shifts from King Arthur himself to other characters, such as various Knights of the Round Table. Arthurian literature thrived during the Middle Ages but waned in the centuries that followed until it experienced a major resurgence in the 19th century. In the 21st century, the legend lives on, not only in literature but also in adaptations for theatre, film, television, comics and other media.")
            .build();
    public static final Document ARTHUR_BRITTANIA = DocumentImpl.newDocumentBuilder()
            .addTextField("title", "Arthur, King of the Britons")
            .addTextField("subtitle", "A biography by David Nash Ford")
            .addTextField("paragraph", "Arthur, it seems, is claimed as the King of nearly every Celtic Kingdom known. The 6th century certainly saw many men named Arthur born into the Celtic Royal families of Britain but, despite attempts to identify the great man himself amongst them, there can be little doubt that most of these people were only named in his honour. Princes with other names are also sometimes identified with \"Arthwyr\" which is thought by some to be a title similar to \"Vortigern\". ")
            .addTextField("paragraph", "Geoffrey of Monmouth recorded Arthur as a High-King of Britain. He was the son of his predecessor, Uther Pendragon and nephew of King Ambrosius. As a descendant of High-King Eudaf Hen's nephew, Conan Meriadoc, Arthur's grandfather, had crossed the Channel from Brittany and established the dynasty at the beginning of the 5th century. The Breton King Aldrien had been asked to rescue Britain from the turmoil in which it found itself after the Roman administration had departed. He sent his brother, Constantine, to help. Constantine appears to have been the historical self-proclaimed British Emperor who took the last Roman troops from Britain in a vain attempt to assert his claims on the Continent in 407. Chronologically speaking, it is just possible he was King Arthur's grandfather. Arthur's Breton Ancestry was recorded by Gallet. ")
            .addTextField("paragraph", "Riothamus the King\n"
            + "Geoffrey Ashe argues that King Arthur was an historical King in Brittany known to history as Riothamus, a title meaning \"Greatest-King\". His army is recorded as having crossed the channel to fight the Visigoths in the Loire Valley in 468. Betrayed by the Prefect of Gaul, he later disappeared from history. Ashe does not discuss Riothamus' ancestry. He, in fact, appears quite prominently in the pedigree of the Kings of DomnonŽe, dispite attempts to equate him with a Prince of Cornouaille named Iaun Reith. Riothamus was probably exiled to Britain during one of the many civil wars that plagued Brittany. He later returned in triumph to reclaim his inheritance, but was later killed in an attempt to expel Germanic invaders. The main trouble with this Arthurian identification is that it pushes King Arthur back fifty years from his traditional period at the beginning of the sixth century (See Ashe 1985). ")
            .build();
    public static final Document ARTHURIANE = DocumentImpl.newDocumentBuilder()
            .addTextField("title", "Studies in early medieval history & legend")
            .addTextField("paragraph", "Welcome to Arthuriana. This is the personal academic website of Dr Thomas Green, which I have maintained since 1998. I am currently engaged in research at the University of Oxford; my principal research interests lie in the history, archaeology, place-names and literature of early medieval Britain. I have published a number of articles and books on these topics, with a particular focus on the early Arthurian legend and Anglian-British interaction in this period. I also lecture on these matters and have appeared on various national and local media outlets, including BBC One and Radio 5 Live. ")
            .addTextField("paragraph", "This website offers details of some of my work, divided into three sections accessible from the menu on the left. Although a brief list of some of my recent publications is included below, more information is provided in the Recent Publications section, including various options for downloading and reading those papers that I'm able to make freely available online. Historical Research offers details of some of my research into the history of early medieval Britain, including my recent academic monongraph entitled Britons and Anglo-Saxons. Finally, Arthurian Resources and Studies offers access to a variety of material - both scholarly and more informal - relating to the early Arthurian legend, especially its Welsh manifestations.")
            .build();
    public static final Document ARTHUR_KREWE = DocumentImpl.newDocumentBuilder()
            .addTextField("title", "Krewe of King Arthur")
            .addTextField("subtitle", "New Orleans' Friendliest Mardi Gras Krewe")
            .addTextField("heading", "Welcome to King Arthur!")
            .addTextField("paragraph", "Each year, the Krewe of King Arthur's 450+ members parade on the first Sunday of Mardi Gras, commonly known as “Family Sunday\" in the Carnival schedule.")
            .addTextField("paragraph", "The Krewe of King Arthur parades down beautiful St. Charles Avenue on the historic Uptown New Orleans route before turning onto Canal Street in front of tens of thousands of spectators of all ages.   Dozens of marching units (floats, bands, etc.) make up the King Arthur procession.   ")
            .addTextField("paragraph", "Mark your calendars now for Sunday, February 23, 2014 when King Arthur once again takes to the streets of Uptown New Orleans!")
            .addTextField("paragraph", "Be a part of the fun!  If you're interested in being a part of New Orleans Mardi Gras, we encourage you to consider membership in our Krewe.")
            .addTextField("link", "Contact the Krewe")
            .build();
    
    public static final List<Document> ALL_DOCUMENTS = Arrays.asList(ARTHUR_WIKIPEDIA, ARTHUR_BRITTANIA, ARTHURIANE, ARTHUR_KREWE);

    private HardCodedDocuments() {
    }
}
