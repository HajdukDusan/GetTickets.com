Vue.component("korisnik-karte", {
  data: function () {
    return {
      cookie: "",
      role: "",
      karte: [],
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.role = localStorage.getItem("role");
    this.loadKarte();
  },
  template: `
    <div>
    
    <korisnik-nav></korisnik-nav>
    <link rel="stylesheet" href="css/page_style.css" type="text/css">
    <b-card id="page_content" style= "height: 100vh;
    overflow: hidden;
    overflow-y: scroll; 
    text-align: center;">
    
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
                    </b-card-text>
                    
                    <div v-if="checkDate(karta)" >
                      <b-button v-on:click="otkaziKartu(karta)" variant="danger">Otkazi</b-button>
                    </div>
                     </b-card>
                     
                    </b-col>
                    </div>
                  </b-row>
    
    </b-card>
    
    </div>

    `,
  methods: {
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
          this.loadKarte();
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
