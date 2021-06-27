Vue.component("home", {
  data: function () {
    return {
      manifestacije: [],
      searchNaziv: "",
      searchGrad: "",
      pocetakDatum: "2019-06-03",
      krajDatum: "2021-06-03",
      minCena: 0,
      maxCena: 0,
    };
  },
  mounted() {
    this.getManifestations();
  },
  template: `
    <div>
        <default-nav></default-nav>
      <link rel="stylesheet" href="css/page_style.css" type="text/css">
	      <b-card id="page_content" style= "height: 100vh;
  overflow: hidden;
  overflow-y: scroll; 
  text-align: center;">
    
          <b-row>
            <b-col>
                <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Naziv</b-input-group-text>
                    </template>
                  <b-form-input
                      placeholder="Pretrazi po nazivu..."
                      v-on:input="getManifestationsSearched"
                      v-model="searchNaziv"
                      type="search"/>
                </b-input-group>
                </b-col>
               <b-col>
                  <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Grad</b-input-group-text>
                    </template>
                  <b-form-input
                      placeholder="Pretrazi po gradu..."
                      v-on:input="getManifestationsSearched"
                      v-model="searchGrad"
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
                      v-model="maxCena"
                      v-on:input="getManifestationsSearched"
                      type="number"
                      min = "0"/>
                      </b-input-group>
                </b-col>
                <b-col>
                 <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Cena od:</b-input-group-text>
                    </template>
                  <b-form-input
                      placeholder="Minimalnu cenu.."
                      v-model="minCena"
                      v-on:input="getManifestationsSearched"
                      type="number"/>
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
                                  v-model="pocetakDatum"
                      v-on:input="getManifestationsSearched"
                      type="date"/>
                      </b-input-group>
                </b-col>
                                <b-col>
                <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Datum do:</b-input-group-text>
                    </template>
                    <b-form-input
                                  v-model="krajDatum"
                      v-on:input="getManifestationsSearched"
                      type="date"/>
                      </b-input-group>
                </b-col>
                
                </b-row>

              <br>
              <b-row>

              </b-row>

                  <b-row >
                  <div class ="col-md-4" v-for="manifestacija in manifestacije">
                  <b-col>
                   <b-card
                      style="margin: 10px auto; max-width: 300px"
                      :header="manifestacija.name"
                      class="text-left"
                      bg-variant="light"
                      :img-src="getImgUrl(manifestacija)"
                  >
                    <b-card-text>
                      Tip: {{ manifestacija.manifestationType }} <br>
                      Datum: {{ fixDate(manifestacija) }} <br>
                      Cena karte: {{ manifestacija.regularPrice  }} <br>
                      Lokacija: {{ manifestacija.location.address  }}, {{ manifestacija.location.city  }}, {{ manifestacija.location.country  }}<br>
                      <div v-if="manifestacija.status == false">
                        Prosecna ocena: {{ manifestacija.regularPrice  }} <br>
                      </div>
             		    </b-card-text>
                     <b-button v-on:click="loadManifestacija(manifestacija)" variant="primary">Pogledajte manifestaciju</b-button>
                     </b-card>
                     
                    </b-col>
                    </div>
                  </b-row>
    </b-card>
    </div>
    `,

  methods: {
    loadManifestacija(manifestacija) {
      localStorage.setItem("manifestacija", manifestacija.name);
      this.$router.push("/prikaz-manifestacije");
    },
    fixDate(manifestacija) {
      manifestacija.dateTime = manifestacija.dateTime.split("T")[0];
      return manifestacija.dateTime;
    },
    getManifestations() {
      axios.get(`rest/manifestation/manifestations`).then((response) => {
        this.manifestacije = response.data;
        console.log(response.data);
      });
      console.log(this.manifestacije);
    },
    getManifestationsSearched() {
      if (this.searchNaziv === "") {
        this.searchNaziv = '""';
      }
      if (this.searchGrad === "") {
        this.searchGrad = '""';
      }
      if (this.maxCena == "") {
        this.maxCena = 0;
      }
      if (this.minCena == "") {
        this.minCena = 0;
      }
      axios
        .get(
          `rest/manifestation/manifestationsSeached/naziv=${this.searchNaziv}/grad=${this.searchGrad}/min=${this.minCena}/max=${this.maxCena}/minDatum=${this.pocetakDatum}/maxDatum=${this.krajDatum}`
        )
        .then((response) => {
          //axios.get(`rest/manifestation/manifestationsSeached/naziv=""/grad=${this.searchGrad}`).then((response) => {
          this.manifestacije = response.data;
          console.log(response.data);
        });
      console.log(this.manifestacije);
      if (this.searchGrad === '""') {
        this.searchGrad = "";
      }
      if (this.searchNaziv === '""') {
        this.searchNaziv = "";
      }
    },
    getImgUrl(manifestacija) {
      return manifestacija.eventPoster;
    },
  },
});
