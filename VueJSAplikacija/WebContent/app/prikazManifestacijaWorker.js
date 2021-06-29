Vue.component("prikaz-manifestacija-worker", {
  data: function () {
    return {
      cookie: "",
      role: "",
      manifestacije: [],
      zoom: 15,
      center: [20.395587077688546, 45.38230342193068],
      location: [20.395587077688546, 45.38230342193068],
      rotation: 0,
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.role = localStorage.getItem("role");
    this.getManifestation();
  },
  template: `
  <div>
  
  
  
    <worker-nav></worker-nav>

          <link rel="stylesheet" href="css/page_style.css" type="text/css">
          <b-card id="page_content" style= "height: 100vh;
          overflow: hidden;
          overflow-y: scroll; 
          text-align: center;">
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
                    </b-card-text>

                    <b-button varient="primary" v-on:click="izmenaManifestacije(manifestacija)">Izmeni manifestaciju</b-button>
                     </b-card>
                     
                    </b-col>
                    </div>
                  </b-row>
    
        </b-card>
  
  
  </div>
  `,

  methods: {
    onSubmit(manifestacija) {},
    getImgUrl(manifestacija) {
      return manifestacija.eventPoster;
    },
    izmenaManifestacije(manifestacija) {
      localStorage.setItem("manifestacija", manifestacija.name);
      this.$router.push("/izmena-manifestacije");
    },
    getManifestation() {
      axios
        .get(`rest/manifestation/manifestationsWorker/cookie=${this.cookie}`)
        .then((response) => {
          this.manifestacije = response.data;
          console.log(this.manifestacije);
        });
    },
  },
});
