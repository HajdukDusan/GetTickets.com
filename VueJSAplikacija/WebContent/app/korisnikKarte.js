Vue.component("korisnik-karte", {
  data: function () {
    return {
      cookie: "",
      role: "",
      karte: [],
      manifestacija: "",
      cenaOd: "",
      cenaDo: "",
      datumOd: "",
      datumDo: "",
      sortBy: "",
      tipKarte: "",
      statusKarte: "",
      smer: "Rastuce",
      smerOptions: ["Rastuce", "Opadajuce"],
      sortByOptions: ["Ime manifestacija", "Cena Karte", "Datum odrzavanja"],
      filterByTip: ["", "REGULAR", "FAN_PIT", "VIP"],
      filterByStatus: ["", "true", "false"],
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.role = localStorage.getItem("role");
    this.getCardsSearched();
  },
  template: `
    <div>
    
    <korisnik-nav></korisnik-nav>
    <link rel="stylesheet" href="css/page_style.css" type="text/css">
    <b-card id="page_content" style= "height: 100vh;
    overflow: hidden;
    overflow-y: scroll; 
    text-align: center;">
    

                <b-row>
            <b-col>
                <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Manifestacija</b-input-group-text>
                    </template>
                  <b-form-input
                      placeholder="Pretrazi po nazivu..."
                      v-on:input="getCardsSearched"
                      v-model="manifestacija"
                      type="search"/>
                </b-input-group>
                </b-col>
               <b-col>
                  <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Cena od:</b-input-group-text>
                    </template>
                  <b-form-input
                      placeholder="Cena od.."
                      v-on:input="getCardsSearched"
                      v-model="cenaOd"
                      type="search"/>
                       </b-input-group>
                </b-col>
                                <b-col>
                                 <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Cena do:</b-input-group-text>
                    </template>
                <b-form-input
                      placeholder="Maksimalnu cenu.."
                      v-model="cenaDo"
                      v-on:input="getCardsSearched"
                      type="number"
                      min = "0"/>
                      </b-input-group>
                </b-col>
                </b-row>
                <br>
              <b-row>
            <b-col>
                <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Datum od:</b-input-group-text>
                    </template>
                  <b-form-input
                      v-on:input="getCardsSearched"
                      v-model="datumOd"
                      type="date"/>
                </b-input-group>
                </b-col>
               <b-col>
                  <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Datum do:</b-input-group-text>
                    </template>
                  <b-form-input
                      v-on:input="getCardsSearched"
                      v-model="datumDo"
                      type="date"/>
                       </b-input-group>
                </b-col>
                </b-row>
                <br>
                <b-row>
                <b-col>
                  <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Sort by</b-input-group-text>
                    </template>
                <b-form-select
                      placeholder="Izaberite sort"
                      v-model="sortBy"
                      :options="sortByOptions"
                      v-on:input="getCardsSearched"/>
                      </b-input-group>
                </b-col>
                <b-col>
                  <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Smer</b-input-group-text>
                    </template>
                <b-form-select
                      v-model="smer"
                      :options="smerOptions"
                      v-on:input="getCardsSearched"/>
                      </b-input-group>
                </b-col>
                <b-col>
                  <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Tip karte</b-input-group-text>
                    </template>
                <b-form-select
                      placeholder="Izaberite sort"
                      v-model="tipKarte"
                      :options="filterByTip"
                      v-on:input="getCardsSearched"/>
                      </b-input-group>
                </b-col>
                <b-col>
                  <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Status karte</b-input-group-text>
                    </template>
                <b-form-select
                      placeholder="Izaberite sort"
                      v-model="statusKarte"
                      :options="filterByStatus"
                      v-on:input="getCardsSearched"/>
                      </b-input-group>
                </b-col>
                </b-row>






                  <b-row >
                  <div class ="col-md-4" v-for="karta in karte">
                  <b-col>
                   <b-card
                      style="margin: 10px auto; max-width: 300px"
                      class="text-left"
                      bg-variant="light"
                  >
                    <b-card-text>
                      Manifestacija: {{ karta.manifestation }} <br>
                      Cena: {{karta.price}} <br>
                      Tip: {{karta.cardType}} <br>
                      Datum: {{karta.manifestationDate}} <br>
                      Status: {{karta.status}} <br>
                    </b-card-text>
                    
                    <div v-if="checkDate(karta)" >
                      <div v-if="karta.status">
                        <b-button v-on:click="otkaziKartu(karta)" variant="danger">Otkazi</b-button>
                      </div>

                    </div>
                     </b-card>
                     
                    </b-col>
                    </div>
                  </b-row>
    
    </b-card>
    
    </div>

    `,
  methods: {
    getCardsSearched() {
      axios
        .get(`rest/card/searched`, {
          params: {
            cookie: this.cookie,
            manifestacija: this.manifestacija,
            cenaOd: this.cenaOd,
            cenaDo: this.cenaDo,
            datumOd: this.datumOd,
            datumDo: this.datumDo,
            sortBy: this.sortBy,
            tipKarte: this.tipKarte,
            statusKarte: this.statusKarte,
            smer: this.smer,
          },
        })
        .then((resp) => {
          this.karte = resp.data;
        });
    },
    checkDate(card) {
      var today = new Date();
      var nextWeek = new Date(
        today.getFullYear(),
        today.getMonth(),
        today.getDate() + 7
      );
      const sevenDays = 7 * 60 * 60 * 24 * 1000;
      console.log(nextWeek - Date.parse(card.manifestationDate.split("T")[0]));
      if (nextWeek - Date.parse(card.manifestationDate.split("T")[0]) > 0) {
        return false;
      } else {
        return true;
      }
    },
    otkaziKartu(card) {
      console.log(card);
      axios
        .get(`rest/card/cancelCard/cookie=${this.cookie}/id=${card.id}`)
        .then((response) => {
          this.getCardsSearched();
        });
    },
    loadKarte() {
      axios
        .get(`rest/card/userCards/cookie=${this.cookie}`)
        .then((response) => {
          console.log(response.data);
          this.karte = response.data;
        });
    },
  },
});
