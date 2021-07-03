Vue.component("pregled-manifestacija", {
  data: function () {
    return {
      cookie: "",
      role: "",
      manifestacije: [],
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.role = localStorage.getItem("role");
    this.loadManifestacije();
  },
  template: `
    <div>
    <admin-nav></admin-nav>
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
                      Datum: {{ fixDate(manifestacija) }} <br>
                      Cena karte: {{ manifestacija.regularPrice  }} <br>
                      Lokacija: {{ manifestacija.location.address  }}, {{ manifestacija.location.city  }}, {{ manifestacija.location.country  }}<br>
                      <div v-if="manifestacija.status == false">
                        Prosecna ocena: {{ manifestacija.regularPrice  }} <br>
                      </div>
             		    </b-card-text>
                     </b-card>
                     
                    </b-col>
                    </div>
                  </b-row>
    
        </b-card>
    </div>

    `,
  methods: {
    fixDate(manifestacija) {
      manifestacija.dateTime = manifestacija.dateTime.split("T")[0];
      return manifestacija.dateTime;
    },
    getImgUrl(manifestacija) {
      return manifestacija.eventPoster;
    },
    loadManifestacije() {
      axios.get(`rest/manifestation/manifestations`).then((response) => {
        console.log(response.data);
        this.manifestacije = response.data;
      });
    },
  },
});
